package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.config.FileStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FIleService {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    //private final Path absolutePath = Paths.get(fileStorageConfig.getPath()); здесь на время разработки абсолютный путь другой будет
    private final Path absolutePath = Paths.get("C:\\Users\\nikita\\Desktop\\pulsus-project\\frontend\\pulsus-frontend\\public\\uploads");
    public String getAbsolutePath(String relativePath) {
        Path path1 = Paths.get(fileStorageConfig.getPath());
        System.out.println(path1);
        Path path2 = Paths.get(relativePath);
        System.out.println(path2);
        Path path = path1.resolve(path2);
        String finalPath = path.toString().replace('\\', '/');
        return finalPath;
    }

    public void CreateUserDir() {
        Path absolute = Paths.get(fileStorageConfig.getPath());
        String directoryPath = "/path/to/directory";
    }

}
