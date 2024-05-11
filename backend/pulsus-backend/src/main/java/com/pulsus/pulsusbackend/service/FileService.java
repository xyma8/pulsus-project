package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.config.FileStorageConfig;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.util.FilePaths;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    private FileOnServerService fileOnServerService;

    FilePaths filePaths;

    //private final Path absolutePath = Paths.get(fileStorageConfig.getPath());
    private final Path absolutePath = Paths.get("C:\\Users\\basce\\Desktop\\pulsus-project\\frontend\\pulsus-frontend\\public\\uploads");

    public String defaultProfilePic() {
        String path = getAbsolutePath(filePaths.DEFAULT_PROFILE_PICTURE_M);
        return path;
    }

    public String profilePic(Long userId) {
        String path = getAbsolutePath("users/" + userId + "/pictures/" + userId + ".jpg");
        File file = new File(path);

        if(!file.exists()) {
            path = getAbsolutePath(filePaths.DEFAULT_PROFILE_PICTURE_M);
        }

        return path;
    }

    public void createUserDirs(Long userId) {
        String userPath = createPath(String.format("users/%s", userId));
        String picturesPath = createPath(String.format("users/%s/pictures", userId));
        String activitiesPath = createPath(String.format("users/%s/workouts", userId));

        CreateDir(userPath);
        CreateDir(picturesPath);
        CreateDir(activitiesPath);
    }

    public void uploadUserProfilePic(Long userId, MultipartFile file) throws IOException {
        String directoryPath = createPath(String.format("users/%s/pictures", userId));

        String filePath = directoryPath + "/" + userId + ".jpg";

        BufferedImage resizedProfilePic = resizeImage(file, 300, 300);
        byte[] compressedProfilePic = compressAndConvertPic(resizedProfilePic);
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(compressedProfilePic);
        fos.close();
    }

    public void uploadUserPic(Long userId, MultipartFile file) throws IOException {

    }

    public String uploadTrackFile(Long userId, MultipartFile file) throws IOException{
        String directoryPath = createPath(String.format("users/%s/workouts", userId));
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String randomName = userId + UUID.randomUUID().toString();

        String filePath = directoryPath + "/" + randomName +  "." + extension;


        File newFile = new File(filePath);
        file.transferTo(newFile);

        return filePath;
    }

    public String getSHA256(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        InputStream inputStream = file.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    private String getAbsolutePath(String relative) {
        Path relativePath = Paths.get(relative);
        Path path = absolutePath.resolve(relativePath);
        String finalPath = path.toString().replace('\\', '/');
        return finalPath;
    }

    /*
    private void addFileInfoToDB() {

    }
    */

    private byte[] compressAndConvertPic(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        // Сжать изображение в формат JPG
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] compressedBytes = baos.toByteArray();

        return compressedBytes;
    }

    private byte[] compressAndConvertPic(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] compressedBytes = baos.toByteArray();

        return compressedBytes;
    }

    private BufferedImage resizeImage(MultipartFile file, int targetWidth, int targetHeight) throws IOException {
        byte[] bytes = file.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage originalBufferedImage = ImageIO.read(bais);

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalBufferedImage, 0, 0, targetWidth, targetHeight, null);

        return resizedImage;
    }

    private String createPath(String relativePath) {
        Path relative = Paths.get(relativePath);
        Path path = absolutePath.resolve(relative);
        String finalPath = path.toString().replace('\\', '/');

        return finalPath;
    }

    private void CreateDir(String path) {
        System.out.println(path);
        File directory = new File(path);

        if (!directory.exists()) {
            boolean success = directory.mkdirs();

            if (success) {
                System.out.println("Папка создана успешно");
            } else {
                System.out.println("Не удалось создать папку");
                throw new InternalServerException("Internal error");
            }
        } else {
            System.out.println("Папка уже существует");
        }

    }

}
