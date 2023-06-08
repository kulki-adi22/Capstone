package com.example.myapplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageUploader {
    private static final String LINE_FEED = "\r\n";
    private String boundary;
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;

    public List<String> uploadImages(String requestURL, List<File> imageFiles) throws IOException {
        boundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

        List<String> response = new ArrayList<>();

        for (File imageFile : imageFiles) {
            addFilePart("image", imageFile);
        }

        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }

        httpConn.disconnect();
        return response;
    }

    private void addFilePart(String fieldName, File uploadFile) throws IOException {
        String fileName = uploadFile.getName();

        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        //writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }
}

