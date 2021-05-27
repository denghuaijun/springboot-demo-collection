package com.dhj.demo.business.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import org.bson.*;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BsonFileUtil
 *
 * @author denghuaijun@eversec.cn
 * @date 2021/5/25 19:09
 * @Description java操作bson二进制文件读写工具类
 */
public class BsonFileUtil {
        private static Logger logger = LoggerFactory.getLogger(BsonFileUtil.class);

    /**
     * 解析bson二进制文件
      * @param filePath 文件地址
     * @return bson对象集合
     */
    public static List<BSONObject> parseBsonFile(String filePath)  {
        File file = new File(filePath);
        int count = 0;
        InputStream inputStream = null;
        List<BSONObject> bsonObjectList =new CopyOnWriteArrayList<>();
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            BSONDecoder decoder = new BasicBSONDecoder();
            while (inputStream.available() > 0) {
                BSONObject obj = decoder.readObject(inputStream);
                if(obj == null){
                    break;
                }
                bsonObjectList.add(obj);
                count++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        System.err.println(String.format("%s objects read", count));
        return bsonObjectList;
    }
    /**
     *
     * @param list 集合数据
     * @param filePath 文件保存的路径
     * @param fileName 文件名
     * @throws FileNotFoundException
     */
    public static void writeBsonStringToFile(List<Map<String,Object>> list,String filePath,String fileName) throws IOException {
        File file =new File(filePath,fileName);
        if (!file.exists()){
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        writeBsonStringToFile(list,outputStream);
    }

    /**
     *
     * @param list 集合数据
     * @param filePath 文件保存的路径
     * @param fileName 文件名
     * @throws FileNotFoundException
     */
    public static void writeBsonBinaryToFile(List<Map<String,Object>> list,String filePath,String fileName) throws IOException {
        File file =new File(filePath,fileName);
        if (!file.exists()){
            if (!file.getParentFile().exists()){
                file.mkdir();
            }
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        writeBsonBinaryToFile(list,outputStream);
    }
    /**
     *将数据以bsonObject字符串形式存入文件
     * @param list 对象集合兼容map object
     * @param file
     * @throws IOException
     */
    public static void writeBsonStringToFile(List<Map<String,Object>> list,File file) throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(file);
        writeBsonStringToFile(list,outputStream);
    }
    /**
     *将数据以bson二进制形式存入文件
     * @param list 对象集合兼容map object
     * @param file
     * @throws IOException
     */
    public static void writeBsonBinaryToFile(List<Map<String,Object>> list,File file) throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(file);
        writeBsonBinaryToFile(list,outputStream);
    }
    /**
     *将数据以bson二进制形式存入文件
     * @param list 对象集合兼容map object
     * @param outputStream
     * @throws IOException
     */
    public static void writeBsonBinaryToFile(List<Map<String,Object>> list, OutputStream outputStream){
        list.forEach(m->{
            String someJSONString = JSONObject.toJSONString(m);
            BsonDocument bson = BsonDocument.parse(someJSONString);
            BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
            BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
            new BsonDocumentCodec().encode(writer, bson, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
            byte[] byteArr = outputBuffer.toByteArray();
            try {
                outputStream.write(byteArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        close(null,outputStream);
    }
    /**
     *将数据以bson对象串形式存入文件
     * @param list 对象集合兼容map object
     * @param outputStream
     * @throws IOException
     */
    public static void writeBsonStringToFile(List<Map<String,Object>> list, OutputStream outputStream){
        List<BSONObject> bsonObjectList = new ArrayList<>();
        BSONEncoder bsonEncoder = new BasicBSONEncoder();

        try {
            OutputStreamWriter writer= new OutputStreamWriter(outputStream,"UTF-8");
            list.forEach(m->{
                BSONObject bsonObject = new BasicBSONObject(m);
                bsonObjectList.add(bsonObject);
            });
            bsonObjectList.forEach(bsonObject -> {
                byte[] encode = bsonEncoder.encode(bsonObject);
                try {
                /*BasicOutputBuffer basicOutputBuffer =new BasicOutputBuffer();
                basicOutputBuffer.write(encode);
                basicOutputBuffer.pipe(outputStream);*/
                writer.write(bsonObject.toString()+"\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        close(null,outputStream);
    }

    /**
     * 将集合对象转换为bson对象，进而转为jacksonjson格式写入文件中
     * @param mapList
     * @param file
     */
    public static void writeBsonToJson(List<Map<String,Object>> mapList, File file)  {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream =new FileOutputStream(file);
            OutputStreamWriter writer  = new OutputStreamWriter(fileOutputStream);;
            mapList.forEach(map->{
                String someJSONString = JSONObject.toJSONString(map);
                BsonDocument bson = BsonDocument.parse(someJSONString);
                JsonNode jsonNode = null;
                try {
                    jsonNode = bsonDocumentToJsonNode(bson);
                    writer.write(jsonNode.toPrettyString()+"\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream !=null){
                try{
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }
    /**
     * Document对象转换输入流
     * @param document
     * @return
     */
    public static InputStream documentToInputStream(final Document document) {
        BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
        BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
        new DocumentCodec().encode(writer, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        return new ByteArrayInputStream(outputBuffer.toByteArray());
    }
    /**
     * bson Document对象转换jackson json
     * @param document
     * @return
     */
    public static JsonNode documentToJsonNode(final Document document) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new BsonFactory());
        InputStream is = documentToInputStream(document);
        return mapper.readTree(is);
    }
    public static InputStream bsonDocumentToInputStream(final BsonDocument document) {
        BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
        BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
        new BsonDocumentCodec().encode(writer, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        return new ByteArrayInputStream(outputBuffer.toByteArray());
    }

    public static JsonNode bsonDocumentToJsonNode(final BsonDocument document) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new BsonFactory());
        InputStream is = bsonDocumentToInputStream(document);
        return mapper.readTree(is);
    }

    public  static void close(InputStream inputStream,OutputStream outputStream){
        if (inputStream !=null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream !=null){
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

