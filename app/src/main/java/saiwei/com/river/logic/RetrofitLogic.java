package saiwei.com.river.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import saiwei.com.river.service.FileUploadService;
import saiwei.com.river.service.NetWorkService;

/**
 * Created by saiwei on 9/22/17.
 */

public class RetrofitLogic {

    private NetWorkService service;

    private FileUploadService fileUploadService;

    /**
     * 测试
     */
//    public static final String BASE_URL = "http://www.longyan.cn/riverManageTest/api/app/";

    /**
     * 正式
     */
    public static final String BASE_URL = "http://www.longyan.cn/riverManage/api/app/";


    public static final String IMAGE_BASE_URL = "http://www.longyan.cn/image/upload/user/";
//    public static final String IMAGE_BASE_GET_URL = "http://www.longyan.cn/image/get";
    public static final String IMAGE_BASE_GET_URL = "http://www.longyan.cn/file/get";




//    http://www.longyan.cn/image/upload/user/default

    /**
     * 单例，静态变量。请记得销毁
     */
    private static RetrofitLogic instance = null;

    public static RetrofitLogic getInstance() {
        if (instance == null) {
            instance = new RetrofitLogic();
        }
        return instance;
    }

    private RetrofitLogic(){

        initRetrofit();
        initFileRetrofit();

    }

    public void initRetrofit(){

//        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
//        OkHttpClient client=httpBuilder.readTimeout(3, TimeUnit.MINUTES)
//                .connectTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES) //设置超时
//                .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //手动指定Gson解析方式,可指定不同Convert来使用如Jackson，XML等解析方式
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
//                    .client(client)
                    .build();

            //使用Retrofit类生成接口NetWorkService的实现
            service = retrofit.create(NetWorkService.class);
    }

    public void initFileRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                //手动指定Gson解析方式,可指定不同Convert来使用如Jackson，XML等解析方式
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //使用Retrofit类生成接口NetWorkService的实现
        fileUploadService = retrofit.create(FileUploadService.class);
    }



    public NetWorkService getService(){
        return service;
    }

    public FileUploadService getFileUploadService(){
        return fileUploadService;
    }


    public Gson buildGson() {
//        Gson gson  = new GsonBuilder().registerTypeAdapterFactory(
//                new NullStringToEmptyAdapterFactory()).create();
////然后用上面一行写的gson来序列化和反序列化实体类type
//        gson.fromJson(json, type);
//        gson.toJson(type);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();


        return gson;
    }

    public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }


    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */
//    public static Gson buildGson() {
//        if (gson == null) {
//            gson = new GsonBuilder()
//                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
//                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
//                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
//                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
//                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
//                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
//                    .create();
//        }
//        return gson;
//    }

    public class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return 0l;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }


}
