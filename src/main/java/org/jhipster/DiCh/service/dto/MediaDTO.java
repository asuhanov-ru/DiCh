package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.jhipster.dich.domain.Media} entity.
 */
public class MediaDTO implements Serializable {

    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String fileType;

    private String fileDesc;

    private CollectionsDTO collections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public CollectionsDTO getCollections() {
        return collections;
    }

    public void setCollections(CollectionsDTO collections) {
        this.collections = collections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaDTO)) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mediaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileDesc='" + getFileDesc() + "'" +
            ", collections=" + getCollections() +
            "}";
    }
}
