package springbootgradleaws.springbootgradleaws;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Random;

@RestController
public class TestController {

    Region region = Region.AP_NORTHEAST_2;
//    S3Client s3Client = S3Client.builder().region(region).build();

    S3Client s3Client = S3Client.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .build();

    String bucket = "11st-bucket-test";
    String key = "img.jpg";

    @RequestMapping(value = "/hello")
    public String hello() {

        System.out.println("Enter hello");
        return "hello world";
    }

    // Create Bucket 생성
    @RequestMapping("/createbucket")
    public String createBucket() {

        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(region.id())
                        .build())
                .build();

        s3Client.createBucket(createBucketRequest);

        return "Create Bucket";
    }

    // Get Bucket List
    // for each 사용방법 정리하기
    @RequestMapping("/list")
    public String getList() {
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);

        listBucketsResponse.buckets().forEach(x -> System.out.println(x.name()));
        return "Get List";
    }

    // Delete Bucket
    @RequestMapping("/delete")
    public String deleteBucket() {

        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucket).build();
        s3Client.deleteBucket(deleteBucketRequest);

        return "Delete Bucket";
    }

    private static ByteBuffer getRandomByteBuffer(int size) {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }

//    file upload
    @RequestMapping("/upload")
    public String uploadToBucket() throws IOException {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).build(),
                RequestBody.fromByteBuffer(getRandomByteBuffer(10_000)));
        return "Upload to Bucket";
    }

//    file download
    @RequestMapping("/download")
    public String downloadToBucket() {

        s3Client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build(),
                ResponseTransformer.toFile(Paths.get("img.jpg")));
        return "Download to Bucket";
    }

//    file delete
    @RequestMapping("/delfile")
    public String deletefile(){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.deleteObject(deleteObjectRequest);
        return "Delete File";
    }
}
