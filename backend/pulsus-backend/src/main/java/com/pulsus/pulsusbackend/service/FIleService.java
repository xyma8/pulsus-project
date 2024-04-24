package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.config.FileStorageConfig;
import com.pulsus.pulsusbackend.util.FilePaths;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

@Service
public class FIleService {

    @Autowired
    private FileStorageConfig fileStorageConfig;

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

    public void CreateUserDirs(Long userId) {
        String userPath = createPath(String.format("users/%s", userId));
        String picturesPath = createPath(String.format("users/%s/pictures", userId));
        String activitiesPath = createPath(String.format("users/%s/activities", userId));

        CreateDir(userPath);
        CreateDir(picturesPath);
        CreateDir(activitiesPath);
    }

    public void AddUserProfilePic(Long userId, MultipartFile file) throws IOException {
        String directoryPath = createPath(String.format("users/%s/pictures", userId));
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        String filePath = directoryPath + "/" + userId + "." + extension;

        File newFile = new File(filePath);
        file.transferTo(newFile);
    }

    public void AddUserPic(Long userId) {

    }

    private String getAbsolutePath(String relative) {
        Path relativePath = Paths.get(relative);
        Path path = absolutePath.resolve(relativePath);
        String finalPath = path.toString().replace('\\', '/');
        return finalPath;
    }

    private String normalizePicExtension(String extension) {
        return null;
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
            }
        } else {
            System.out.println("Папка уже существует");
        }

    }


}
