package joaojunior10.htmltopdf;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
public class HtmlToPdfController {

    @PostMapping
    String convert(@RequestBody String html) throws IOException, InterruptedException {
        html = addEncoding(html);
        File pdfFile = createPdf(html);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(pdfFile));
        removeTmpFile(pdfFile);
        return new String(encoded);
    }

    private String addEncoding(String html) {
        Document doc = Jsoup.parse(html);
        doc.charset(StandardCharsets.UTF_8);
        return doc.html();
    }

    
    private File createPdf(String html) throws IOException, InterruptedException {
        File htmlFile = File.createTempFile("input-", ".html");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(htmlFile, false), StandardCharsets.UTF_8));
        bw.write(html);
        bw.close();
        String pdfFilePath = htmlFile.getParent() + "/output-" + UUID.randomUUID() + ".pdf";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "wkhtmltopdf " + htmlFile.getPath() + " " + pdfFilePath);
        Process process = processBuilder.start();
        int exitVal = process.waitFor();
        if (exitVal != 0) {
            throw new RuntimeException("Error converting the file - Exit: "
                    + exitVal + " Html file: " + html);
        }
        removeTmpFile(htmlFile);
        return new File(pdfFilePath);
    }
    
    private void removeTmpFile(File file){
        boolean result = file.delete();
        if (!result) {
            System.out.println("Temp file " + file.getPath() + " not removed.");
        }
    }
}