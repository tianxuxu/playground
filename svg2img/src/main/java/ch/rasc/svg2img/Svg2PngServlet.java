package ch.rasc.svg2img;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

@WebServlet(urlPatterns = "/svg2png")
public class Svg2PngServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		response.setContentType("image/png");
		response.setHeader("Content-Disposition", "attachment; filename=\"mixedchart.png\";");

		String svg = request.getParameter("svg");

		StringReader stringReader = new StringReader(svg);
		TranscoderInput input = new TranscoderInput(stringReader);

		OutputStream out = response.getOutputStream();
		TranscoderOutput output = new TranscoderOutput(out);

		PNGTranscoder t = new PNGTranscoder();
		try {
			t.transcode(input, output);
		} catch (TranscoderException e) {
			throw new ServletException(e);
		}

		stringReader.close();
		out.close();
	}

}
