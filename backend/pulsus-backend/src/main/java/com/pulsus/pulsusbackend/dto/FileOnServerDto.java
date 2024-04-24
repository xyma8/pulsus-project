package com.pulsus.pulsusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class FileOnServerDto {
    private String path;

    public void setPath(String path) {
        this.path = path.substring(path.indexOf("uploads")); //УДАЛИТЬ И ЗАМЕНИТЬ НА path;
    }

    public String getPath() {
        path = path.substring(path.indexOf("uploads")); //УДАЛИТЬ
        return path;
    }
}
