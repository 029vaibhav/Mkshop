package com.mobiles.msm.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by vaibhav on 22/2/16.
 */
public class JsoupUtilities {

    public HashMap<String, HashMap<String, String>> getData(final String gitHubUrl) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<Document> callableList = new Callable<Document>() {
            @Override
            public Document call() throws Exception {
                Document document = Jsoup.connect(gitHubUrl).get();
                return document;
            }
        };
        Future<Document> submit = executorService.submit(callableList);
        try {
            Document document = submit.get();
            HashMap<String, HashMap<String, String>> product = new HashMap<>();
            Element masthead = document.select("div#specs-list").first();
            Elements table = masthead.select("table");
            for (Element element : table) {
                Elements header = element.select("th");
                String title = header.text();
                Elements a = element.select("a[href~=(.*.term.*|#)]");
                Elements td = element.select("td.nfo");
                int valueSize = td.size();
                HashMap<String, String> hashMap = new HashMap();
                for (int i = 0; i < a.size(); i++) {
                    String prop = a.get(i).text();
                    Log.d("property", prop);
                    if (i < valueSize) {
                        String value = td.get(i).text();
                        hashMap.put(prop, value);
                        Log.d("value", value);
                    }
                }
                product.put(title, hashMap);

            }
            Log.d("product", product.toString());
            return product;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
