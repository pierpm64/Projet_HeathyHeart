package fr.isika.cdi07.projet3demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.isika.cdi07.projet3demo.model.Document;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.TypeDocument;
import fr.isika.cdi07.projet3demo.model.TypeLibelleDoc;
import fr.isika.cdi07.projet3demo.modelform.DocumentForm;
import fr.isika.cdi07.projet3demo.services.DocumentService;
import fr.isika.cdi07.projet3demo.services.ProjetService;

@Controller
public class DocumentController {

	private static final Logger LOGGER = Logger.getLogger(DocumentController.class.getSimpleName());
	private static final Long MAXJPEGSIZE = 400000L;
	private static final Long MAXJPEGSIZELOAD = 800000L;

	@Autowired
	private ProjetService projetService;

	@Autowired
	private DocumentService documentService;

	@GetMapping("/NewPictureForm/{id}")
	public String NewPictureForm(@PathVariable(value = "id") Long id, Model model, HttpSession session) {

		Optional<Projet> projet = projetService.getProjetById(id);
		if (!projet.isPresent())
			return "error";
		Optional<Object> errorMsg = Optional.ofNullable(session.getAttribute("ErrorNewPicture"));
		String msgerr = "";
		if (errorMsg.isPresent()) {
			msgerr = errorMsg.get().toString();
			session.removeAttribute("ErrorNewPicture");
		}

		Projet monProjet = projet.get();

		DocumentForm documentForm = new DocumentForm();
		Document document = new Document();
		document.setProjet(projet.get());
		document.setTypeDocument(TypeDocument.JPG);
		documentForm.setDocument(document);
		documentForm.setProjet(monProjet);

		Optional<Document> monDoc = null;
		monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_PRINCIPALE);
		if (monDoc.isPresent())
			documentForm.setImage1(monDoc.get().getIdDocument());
		else
			documentForm.setImage1(-1L);

		monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_SECONDE);
		if (monDoc.isPresent())
			documentForm.setImage2(monDoc.get().getIdDocument());
		else
			documentForm.setImage2(-1L);

		monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_TROISIEME);
		if (monDoc.isPresent())
			documentForm.setImage3(monDoc.get().getIdDocument());
		else
			documentForm.setImage3(-1L);

		model.addAttribute("docform", documentForm);
		model.addAttribute("msgerr", msgerr);

		return "newPictureProjet";
	}

	@PostMapping("/NewUploadPicture")
	public String newSavePicture(@ModelAttribute("docform") DocumentForm documentForm, HttpSession session)
			throws IOException {
		Projet monProjet = documentForm.getProjet();
		TypeLibelleDoc libelDoc = null;

		switch (documentForm.getLibelImage()) {
		case "1":
			libelDoc = TypeLibelleDoc.IMAGE_PRINCIPALE;
			break;
		case "2":
			libelDoc = TypeLibelleDoc.IMAGE_SECONDE;
			break;
		case "3":
			libelDoc = TypeLibelleDoc.IMAGE_TROISIEME;
			break;
		default:
			session.setAttribute("ErrorNewPicture", "Vous n'avez pas selectionné de type d'image");
			return "redirect:/NewPictureForm/" + monProjet.getIdProjet();

		}

		if (documentForm.getImage().getSize() == 0) {
			session.setAttribute("ErrorNewPicture", "Vous n'avez pas selectionné d'image");
			return "redirect:/NewPictureForm/" + monProjet.getIdProjet();
		}

		Optional<Document> opt = documentService.findbyProjetAndLibelle(monProjet, libelDoc);
		if (opt.isPresent()) {
			session.setAttribute("ErrorNewPicture", "Il y a déjà un ce type d'image (" + libelDoc + ") pour le projet");

			return "redirect:/NewPictureForm/" + monProjet.getIdProjet();
		}
		File monFile = File.createTempFile("Image_Healthy_Heart_tmp_",".jpg");
		monFile.mkdirs();

		documentForm.getImage().transferTo(monFile);

		Long fileLen = monFile.length();
		// LOGGER.info("Taille image in : " + fileLen);
		if (fileLen > MAXJPEGSIZE && fileLen < 13000000) {
			File newFile = compressImage(monFile);
			monFile.delete();
			monFile = newFile;
			fileLen = monFile.length();
			// LOGGER.info("fichier compresse :" + monFile.getAbsolutePath() + " / taille : " + monFile.length());
		}
		
		// LOGGER.info("Taille image out : " + fileLen + " / max=" + MAXJPEGSIZELOAD );

		if (fileLen >= MAXJPEGSIZELOAD ) {
			session.setAttribute("ErrorNewPicture", "Selectionnez une image de 5Mo maximum");
			monFile.delete();
			return "redirect:/NewPictureForm/" + monProjet.getIdProjet();
		}

		documentForm.getDocument().setFichier(Files.readAllBytes(monFile.toPath()));
		monFile.delete();

		documentForm.getDocument().setLibelle(libelDoc);
		documentForm.getDocument().setDate(Date.from(Instant.now()));
		documentForm.getDocument().setProjet(monProjet);

		documentService.saveImage(documentForm.getDocument());
		return "redirect:/NewPictureForm/" + monProjet.getIdProjet();
	}

	@RequestMapping(value = "/viewImage/{id}", method = RequestMethod.GET)
	public void getImageAsByteArray(@PathVariable(value = "id") Long id, HttpServletResponse response)
			throws IOException {
		byte[] byteArray = documentService.getDocumentById(id).getFichier();
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// response.setHeader("Content-Disposition", "filename=\"THE FILE NAME\"");
		response.setContentLength(byteArray.length);
		OutputStream os = response.getOutputStream();

		try {
		   os.write(byteArray , 0, byteArray.length);
		} catch (Exception excp) {
		   //handle error
		} finally {
		    os.close();
		}
	}

	@RequestMapping("/DelUploadPicture/{id}")
	public String DelPicture(@PathVariable(value = "id") Long id) {

		Document monDocument = documentService.getDocumentById(id);
		Long idProjet = monDocument.getProjet().getIdProjet();

		documentService.DeleteDocument(monDocument);
		return "redirect:/NewPictureForm/" + idProjet;
	}

	// Compress JPPEG à la volee
	private File compressImage(File imageFile) throws IOException {

	    File compressedImageFile = File.createTempFile("Image_Healthy_Heart_tmp_",".jpg");
	 
	    InputStream is = new FileInputStream(imageFile);
	    OutputStream os = new FileOutputStream(compressedImageFile);
	 
	    Long fileSize = imageFile.length();
	    float quality = MAXJPEGSIZE.floatValue() / fileSize.floatValue();
	    // LOGGER.info("Compression " + imageFile.getAbsolutePath() + " / taille :" + 
	    //		imageFile.length() + " / quality_ratio : " + String.valueOf(quality) +
	    //		"/ destination : " + compressedImageFile.getAbsolutePath());
	 
	    // create a BufferedImage as the result of decoding the supplied InputStream
	    BufferedImage image = ImageIO.read(is);
	
	    // get all image writers for JPG format
	    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	 
	    if (!writers.hasNext())
	        throw new IllegalStateException("No writers found");
	 
	    ImageWriter writer = (ImageWriter) writers.next();
	    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	    writer.setOutput(ios);
	    ImageWriteParam param = writer.getDefaultWriteParam();
	 
	    // compress to a given quality
	    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    param.setCompressionQuality(quality);
	 
	    // appends a complete image stream containing a single image and
	    //associated stream and image metadata and thumbnails to the output
	    writer.write(null, new IIOImage(image, null, null), param);
	 
	    // close all streams
	    is.close();
	    os.close();
	    ios.close();
	    writer.dispose();
	    
	    return compressedImageFile;
	}


}
