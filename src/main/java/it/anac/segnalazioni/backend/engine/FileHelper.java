package it.anac.segnalazioni.backend.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import it.anac.segnalazioni.backend.engine.model.FileDocument;

public class FileHelper {

	public static void main(String[] args) throws IOException {
		FileHelper fh = new FileHelper();
		List<FileDocument> docs = new LinkedList<FileDocument>();
		
		docs.add(new FileDocument(
				"https://www.anticorruzione.it/documents/91439/129009/Avviso+pubblicazione+esito+prova+orale+e+approvazione+graduatoria+finale+F6IT.pdf/cd5c33c9-40d8-2afb-1f1d-01aacd3df9df?t=1589372298123",
				"test_1.pdf"));
		docs.add(new FileDocument(
				"https://www.anticorruzione.it/documents/91439/129009/Avviso+pubblicazione+esito+prova+orale+e+approvazione+graduatoria+finale+F6IT.pdf/cd5c33c9-40d8-2afb-1f1d-01aacd3df9df?t=1589372298123",
				"test_2.pdf"));
		docs.add(new FileDocument(
				"https://www.anticorruzione.it/documents/91439/129009/Avviso+pubblicazione+esito+prova+orale+e+approvazione+graduatoria+finale+F6IT.pdf/cd5c33c9-40d8-2afb-1f1d-01aacd3df9df?t=1589372298123",
				"test_3.pdf"));
		fh.zipMultipleUrls(docs, "test.zip");
	}
	
	public byte[] file2byte(String file) throws IOException
	{
		File f = new File(file);
		return Files.readAllBytes(f.toPath());
	}
	
	public void zipMultipleFiles(List<String> srcFiles, String zippedFile) throws IOException
	{
        FileOutputStream fos = new FileOutputStream(zippedFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();		
	}
	
	public byte[] downloadUrl(URL toDownload) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try {
	        byte[] chunk = new byte[4096];
	        int bytesRead;
	        InputStream stream = toDownload.openStream();

	        while ((bytesRead = stream.read(chunk)) > 0) {
	            outputStream.write(chunk, 0, bytesRead);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return outputStream.toByteArray();
	}
	
	public void byte2file(byte[] start, String end) throws FileNotFoundException, IOException
	{
		File outputFile =new File(end);
		try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
		    outputStream.write(start);
		}
	}
	
	public void zipMultipleUrls(List<FileDocument> docs, String zippedFile) throws IOException
	{
		 List<String> filesToZip = new LinkedList<String>();
		 for (FileDocument toDownload : docs) {
			 String tempFile = System.getProperty("java.io.tmpdir") +
					 		  	File.separator + toDownload.getFilename();
			 byte2file(downloadUrl(toDownload.getUrl()),tempFile);
			 filesToZip.add(tempFile);
		 }
		 
		 zipMultipleFiles(filesToZip,zippedFile);
	}
	
}
