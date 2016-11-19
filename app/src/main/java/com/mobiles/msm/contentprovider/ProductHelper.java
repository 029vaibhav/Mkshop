package com.mobiles.msm.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.mobiles.msm.pojos.models.Product;
import com.mobiles.msm.pojos.models.ProductTable;

import java.util.List;

/**
 * Created by vaibhav on 19/11/16.
 */

public class ProductHelper {

    static String TAG = ProductHelper.class.getSimpleName();

    public static Uri createProduct(ContentResolver resolver, Product item) {
        Uri uri = resolver.insert(ProductTable.CONTENT_URI, ProductTable.getContentValues(item, false));
        if (uri != null) {
            Log.d(TAG, "added new task");
        }
        return uri;
    }

    public static int deleteProduct(ContentResolver resolver, Product item) {
        int result = resolver.delete(ProductTable.CONTENT_URI, ProductTable.FIELD_ID + "=?", new String[]{String.valueOf(item.getId())});
        if (result > 0) {
            Log.d(TAG, "deleted task");
        }
        return result;
    }

    public static Product getProduct(ContentResolver resolver, Long id) {
        Cursor cursor = resolver.query(ProductTable.CONTENT_URI, null, ProductTable.FIELD_ID + "=?", new String[]{String.valueOf(id)}, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return ProductTable.getRow(cursor, true);
        }
        return null;
    }

    public static List<Product> getProductByQuery(ContentResolver resolver, String selection, String[] arg) {
        Cursor cursor = resolver.query(ProductTable.CONTENT_URI, null, selection, arg, null);
        if (cursor != null && cursor.getCount() > 0) {
            List<Product> products = ProductTable.getRows(cursor, false);
            return products;
        }
        return null;
    }

    public static List<Product> getAllProducts(ContentResolver resolver) {
        Cursor cursor = resolver.query(ProductTable.CONTENT_URI, null, null, null, null);
        return ProductTable.getRows(cursor, true);
    }


    public static int updateProduct(ContentResolver resolver, Product item) {
        return resolver.update(ProductTable.CONTENT_URI, ProductTable.getContentValues(item, true), ProductTable.FIELD_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public static void deleteAll(ContentResolver resolver) {
        resolver.delete(ProductTable.CONTENT_URI, null, null);
    }

    public static void bulkUpdateOrCreate(ContentResolver resolver, List<Product> item) {

        for (Product product : item) {
            Product product1 = getProduct(resolver, product.getId());
            if (product1 == null) {
                createProduct(resolver, product);
            } else {
                updateProduct(resolver, product1);
            }
        }
    }

}
