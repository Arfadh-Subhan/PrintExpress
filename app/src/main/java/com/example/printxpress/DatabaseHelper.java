package com.example.printxpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PrintXpressDB";
    private static final int DB_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "UsersTable";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_ADDRESS = "Address";

    // Products Table
    private static final String TABLE_PRODUCTS = "ProductsTable";
    private static final String COLUMN_PRODUCT_ID = "ProductId";
    private static final String COLUMN_PRODUCT_NAME = "ProductName";
    private static final String COLUMN_PRODUCT_CATEGORY = "ProductCategory";
    private static final String COLUMN_PRODUCT_PRICE = "ProductPrice";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "ProductDescription";
    private static final String COLUMN_PRODUCT_IMAGE = "ProductImage";

    // Orders Table
    private static final String TABLE_ORDERS = "OrdersTable";
    private static final String COLUMN_ORDER_ID = "OrderId";
    private static final String COLUMN_USER_ID = "UserId";
    private static final String COLUMN_ORDER_PRODUCT_ID = "OrderProductId";
    private static final String COLUMN_ORDER_PRODUCT_NAME = "OrderProductName";
    private static final String COLUMN_QUANTITY = "Quantity";
    private static final String COLUMN_TOTAL_PRICE = "TotalPrice";
    private static final String COLUMN_STATUS = "Status";
    private static final String COLUMN_ORDER_DATE = "OrderDate";
    private static final String COLUMN_DELIVERY_OPTION = "DeliveryOption";
    private static final String COLUMN_ORDER_ADDRESS = "OrderAddress";
    private static final String COLUMN_FILE_PATH = "FilePath";
    private static final String COLUMN_PAYMENT_METHOD = "PaymentMethod";
    private static final String COLUMN_NOTES = "Notes";

    // Gallery Table
    private static final String TABLE_GALLERY = "GalleryTable";
    private static final String COLUMN_GALLERY_ID = "GalleryId";
    private static final String COLUMN_GALLERY_TITLE = "Title";
    private static final String COLUMN_GALLERY_DESCRIPTION = "Description";
    private static final String COLUMN_GALLERY_IMAGE = "ImageName";
    private static final String COLUMN_GALLERY_PRODUCT_ID = "ProductId";

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTableSQL = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT);";
        db.execSQL(createUsersTableSQL);

        String createProductsTableSQL = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_CATEGORY + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " TEXT, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT, " +
                COLUMN_PRODUCT_IMAGE + " TEXT);";
        db.execSQL(createProductsTableSQL);

        String createOrdersTableSQL = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_ORDER_PRODUCT_ID + " INTEGER, " +
                COLUMN_ORDER_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_TOTAL_PRICE + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_ORDER_DATE + " TEXT, " +
                COLUMN_DELIVERY_OPTION + " TEXT, " +
                COLUMN_ORDER_ADDRESS + " TEXT, " +
                COLUMN_FILE_PATH + " TEXT, " +
                COLUMN_PAYMENT_METHOD + " TEXT, " +
                COLUMN_NOTES + " TEXT);";
        db.execSQL(createOrdersTableSQL);

        String createGalleryTableSQL = "CREATE TABLE " + TABLE_GALLERY + " (" +
                COLUMN_GALLERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GALLERY_TITLE + " TEXT, " +
                COLUMN_GALLERY_DESCRIPTION + " TEXT, " +
                COLUMN_GALLERY_IMAGE + " TEXT, " +
                COLUMN_GALLERY_PRODUCT_ID + " INTEGER);";
        db.execSQL(createGalleryTableSQL);

        insertSampleProducts(db);
        insertSampleGalleryItems(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERY);
        onCreate(db);
    }

    private void insertSampleProducts(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_NAME, "Business Cards");
        values.put(COLUMN_PRODUCT_CATEGORY, "Cards");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 500");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Premium quality business cards, 300gsm, glossy finish");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_business_card");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Flyer (A4)");
        values.put(COLUMN_PRODUCT_CATEGORY, "Flyers");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 300");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Colorful A4 flyer printing, 200gsm, double sided");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_flyer");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "T-Shirt Printing");
        values.put(COLUMN_PRODUCT_CATEGORY, "Merchandise");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 1500");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Custom t-shirt printing, any design, multiple colors");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_tshirt");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Poster (A3)");
        values.put(COLUMN_PRODUCT_CATEGORY, "Posters");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 800");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "High quality A3 poster printing, 250gsm, matte finish");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_poster");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Sticker (100pcs)");
        values.put(COLUMN_PRODUCT_CATEGORY, "Stickers");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 1200");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Custom sticker printing, 100 pieces, waterproof");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_sticker");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Mug Printing");
        values.put(COLUMN_PRODUCT_CATEGORY, "Merchandise");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 900");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Custom mug printing, ceramic, dishwasher safe");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_mug");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Banner (3x2ft)");
        values.put(COLUMN_PRODUCT_CATEGORY, "Banners");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 2500");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Outdoor banner printing, weatherproof, high quality");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_banner");
        db.insert(TABLE_PRODUCTS, null, values);

        values.put(COLUMN_PRODUCT_NAME, "Brochure");
        values.put(COLUMN_PRODUCT_CATEGORY, "Flyers");
        values.put(COLUMN_PRODUCT_PRICE, "LKR 400");
        values.put(COLUMN_PRODUCT_DESCRIPTION, "Tri-fold brochure printing, 150gsm, glossy finish");
        values.put(COLUMN_PRODUCT_IMAGE, "ic_brochure");
        db.insert(TABLE_PRODUCTS, null, values);
    }

    private void insertSampleGalleryItems(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_GALLERY_TITLE, "Business Card Sample");
        values.put(COLUMN_GALLERY_DESCRIPTION, "Modern business card design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample1");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 1);
        db.insert(TABLE_GALLERY, null, values);

        values.put(COLUMN_GALLERY_TITLE, "Flyer Design");
        values.put(COLUMN_GALLERY_DESCRIPTION, "Creative flyer design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample2");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 2);
        db.insert(TABLE_GALLERY, null, values);

        values.put(COLUMN_GALLERY_TITLE, "Poster Design");
        values.put(COLUMN_GALLERY_DESCRIPTION, "High quality poster design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample3");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 4);
        db.insert(TABLE_GALLERY, null, values);

        values.put(COLUMN_GALLERY_TITLE, "T-Shirt Design");
        values.put(COLUMN_GALLERY_DESCRIPTION, "Custom t-shirt design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample1");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 3);
        db.insert(TABLE_GALLERY, null, values);

        values.put(COLUMN_GALLERY_TITLE, "Sticker Design");
        values.put(COLUMN_GALLERY_DESCRIPTION, "Custom sticker design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample2");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 5);
        db.insert(TABLE_GALLERY, null, values);

        values.put(COLUMN_GALLERY_TITLE, "Banner Design");
        values.put(COLUMN_GALLERY_DESCRIPTION, "Outdoor banner design");
        values.put(COLUMN_GALLERY_IMAGE, "ic_sample3");
        values.put(COLUMN_GALLERY_PRODUCT_ID, 7);
        db.insert(TABLE_GALLERY, null, values);
    }

    public boolean insertUser(String name, String email, String password, String phone, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ADDRESS, address);

        long result = database.insert(TABLE_USERS, null, values);

        if (result == -1) {
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkUserByPhone(String phone, String password) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_PHONE + " = ? AND " + COLUMN_PASSWORD + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{phone, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public String getUserName(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            cursor.close();
            return name;
        } else {
            cursor.close();
            return "";
        }
    }

    public String getUserNameByEmailOrPhone(String emailOrPhone) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query;
        if (emailOrPhone.contains("@")) {
            query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS +
                    " WHERE " + COLUMN_EMAIL + " = ?";
        } else {
            query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS +
                    " WHERE " + COLUMN_PHONE + " = ?";
        }

        Cursor cursor = database.rawQuery(query, new String[]{emailOrPhone});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            cursor.close();
            return name;
        } else {
            cursor.close();
            return "";
        }
    }

    public String getUserPhone(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_PHONE + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            String phone = cursor.getString(0);
            cursor.close();
            return phone;
        } else {
            cursor.close();
            return "";
        }
    }

    public String getUserAddress(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_ADDRESS + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            String address = cursor.getString(0);
            cursor.close();
            return address;
        } else {
            cursor.close();
            return "";
        }
    }

    public String getEmailByPhone(String phone) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_PHONE + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{phone});

        if (cursor.moveToFirst()) {
            String email = cursor.getString(0);
            cursor.close();
            return email;
        } else {
            cursor.close();
            return "";
        }
    }

    public String getUserPassword(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            String password = cursor.getString(0);
            cursor.close();
            return password;
        } else {
            cursor.close();
            return "";
        }
    }

    public int getUserId(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        } else {
            cursor.close();
            return -1;
        }
    }

    public boolean updateUserAddress(String email, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);

        int result = database.update(TABLE_USERS, values,
                COLUMN_EMAIL + " = ?", new String[]{email});

        return result > 0;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));

                Product product = new Product(id, name, category, price, description, image);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public Product getProductById(int productId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + COLUMN_PRODUCT_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));

            cursor.close();
            return new Product(id, name, category, price, description, image);
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + COLUMN_PRODUCT_CATEGORY + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
                String cat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));

                Product product = new Product(id, name, cat, price, description, image);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public boolean insertOrder(int userId, int productId, String productName, int quantity,
                               String totalPrice, String deliveryOption, String address,
                               String filePath, String paymentMethod, String notes) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ORDER_PRODUCT_ID, productId);
        values.put(COLUMN_ORDER_PRODUCT_NAME, productName);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_TOTAL_PRICE, totalPrice);
        values.put(COLUMN_STATUS, "Pending");
        values.put(COLUMN_ORDER_DATE, java.time.LocalDateTime.now().toString());
        values.put(COLUMN_DELIVERY_OPTION, deliveryOption);
        values.put(COLUMN_ORDER_ADDRESS, address);
        values.put(COLUMN_FILE_PATH, filePath);
        values.put(COLUMN_PAYMENT_METHOD, paymentMethod);
        values.put(COLUMN_NOTES, notes);

        long result = database.insert(TABLE_ORDERS, null, values);

        if (result == -1) {
            Toast.makeText(context, "Order Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public ArrayList<Order> getOrdersByUserId(int userId) {
        ArrayList<Order> orderList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ORDERS +
                " WHERE " + COLUMN_USER_ID + " = ?" +
                " ORDER BY " + COLUMN_ORDER_ID + " DESC";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                int uId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                int pId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PRODUCT_ID));
                String pName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PRODUCT_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));
                String orderDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));
                String deliveryOption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DELIVERY_OPTION));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ADDRESS));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE_PATH));
                String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES));

                String productImage = "";
                Product product = getProductById(pId);
                if (product != null) {
                    productImage = product.getImagePath();
                }

                Order order = new Order(id, uId, pId, pName, quantity, totalPrice,
                        status, orderDate, deliveryOption, address, filePath,
                        paymentMethod, notes, productImage);
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }

    public Order getOrderById(int orderId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ORDERS +
                " WHERE " + COLUMN_ORDER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
            int uId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            int pId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PRODUCT_ID));
            String pName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));
            String orderDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));
            String deliveryOption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DELIVERY_OPTION));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ADDRESS));
            String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE_PATH));
            String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES));

            String productImage = "";
            Product product = getProductById(pId);
            if (product != null) {
                productImage = product.getImagePath();
            }

            cursor.close();
            return new Order(id, uId, pId, pName, quantity, totalPrice,
                    status, orderDate, deliveryOption, address, filePath,
                    paymentMethod, notes, productImage);
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean updateOrderStatus(int orderId, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        int result = database.update(TABLE_ORDERS, values,
                COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(orderId)});

        return result > 0;
    }

    public ArrayList<GalleryItem> getAllGalleryItems() {
        ArrayList<GalleryItem> galleryList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GALLERY;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_DESCRIPTION));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_IMAGE));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_PRODUCT_ID));

                GalleryItem item = new GalleryItem(id, title, description, imageName, productId);
                galleryList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return galleryList;
    }

    public GalleryItem getGalleryItemByProductId(int productId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GALLERY +
                " WHERE " + COLUMN_GALLERY_PRODUCT_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_DESCRIPTION));
            String imageName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_IMAGE));
            int pId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GALLERY_PRODUCT_ID));

            cursor.close();
            return new GalleryItem(id, title, description, imageName, pId);
        } else {
            cursor.close();
            return null;
        }
    }
}