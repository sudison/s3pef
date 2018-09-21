package com.github.s3perf;

import lombok.val;
import com.amazonaws.AmazonServiceException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class main {
    public static void main(String[] args) throws IOException {

        val bufferSz = Integer.parseInt(args[0]);
        val fileName = args[1];

        try {

            val httpclient = HttpClients.createDefault();
            val httpGet = new HttpGet("https://s3-us-west-1.amazonaws.com/edison-cloudon-test/image.raw");
            val response = httpclient.execute(httpGet);
            val inputStream = response.getEntity().getContent();
            val os = new FileOutputStream(new File(fileName));
            byte[] read_buf = new byte[bufferSz];
            int read_len = 0;
            while ((read_len = inputStream.read(read_buf)) > 0) {
                os.write(read_buf, 0, read_len);
            }
            inputStream.close();
            os.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}

