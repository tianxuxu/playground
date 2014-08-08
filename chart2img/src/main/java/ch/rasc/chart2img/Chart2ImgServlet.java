package ch.rasc.chart2img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;

public class Chart2ImgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static String encodingPrefix = "base64,";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dataUrl = request.getParameter("data");
		int contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length();
		byte[] imageData = Base64.getDecoder().decode(
				dataUrl.substring(contentStartIndex));

		String format = request.getParameter("format");
		if (format == null) {
			format = "png";
		}

		String filename = request.getParameter("filename");
		if (filename == null) {
			filename = "chart";
		}

		if (format.equals("png")) {
			response.setContentType("image/png");
		}
		else if (format.equals("jpeg")) {
			response.setContentType("image/jpeg");
		}
		else if (format.equals("gif")) {
			response.setContentType("image/gif");
		}
		else if (format.equals("pdf")) {
			response.setContentType("application/pdf");
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename
				+ "." + format + "\";");

		if ("png".equals(format)) {
			response.getOutputStream().write(imageData);
		}
		else if ("gif".equals(format)) {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
			ImageIO.write(image, "gif", response.getOutputStream());
		}
		else if ("jpeg".equals(format)) {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
			BufferedImage newImage = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_RGB);
			newImage.createGraphics().drawImage(image, 0, 0, Color.BLACK, null);
			//ImageIO.write(newImage, "jpg", response.getOutputStream());

			ImageOutputStream ios = ImageIO.createImageOutputStream(response
					.getOutputStream());
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwp.setCompressionQuality(0.1f);
			writer.setOutput(ios);
			writer.write(null, new IIOImage(newImage, null, null), iwp);
			writer.dispose();
		}
		else if ("pdf".equals(format)) {

			PDDocument document = new PDDocument();
			PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
			// page.setRotation(90);
			document.addPage(page);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
			// int w = image.getWidth();
			// int h = image.getHeight();
			// BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			// AffineTransform at = new AffineTransform();
			// at.scale(0.8, 0.8);
			// AffineTransformOp scaleOp =
			// new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			// after = scaleOp.filter(image, after);

			try (PDPageContentStream contentStream = new PDPageContentStream(document,
					page)) {

				System.out.println(page.getMediaBox());

				PDPixelMap ximage = new PDPixelMap(document, image);
				// contentStream.drawImage(ximage, 10, 0);

				float scale = 0.7f; // reduce this value if the image is too large
				contentStream.drawXObject(ximage, 20, page.getMediaBox().getHeight() - 20
						- ximage.getHeight() * scale, ximage.getWidth() * scale,
						ximage.getHeight() * scale);
			}

			try {
				document.save(response.getOutputStream());
			}
			catch (COSVisitorException e) {
				throw new IOException(e);
			}
			document.close();
		}

		response.getOutputStream().flush();
	}
}
