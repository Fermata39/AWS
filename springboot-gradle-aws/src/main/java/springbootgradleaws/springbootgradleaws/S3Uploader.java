package springbootgradleaws.springbootgradleaws;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3Uploader {

//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String upload(MultipartFile mltipartfile, String dirName) throws IOException {
//
//        return " ";
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//
//        return amazonS3Client.getUrl(bucket,fileName).toString();
//    }

}
