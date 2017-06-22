package ge.ssoft.chat.core.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zviad on 6/22/17.
 */
@Entity
@Table(name = "generated_images")
public class GenerateImages {

    @Id
    @Column(name = "image_name")
    private String imageName;


    @Basic
    @Column(name = "image_text")
    private String imageText;

    @Basic
    @Column(name = "create_date")
    private Timestamp create_Date;


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public Timestamp getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Timestamp create_Date) {
        this.create_Date = create_Date;
    }
}
