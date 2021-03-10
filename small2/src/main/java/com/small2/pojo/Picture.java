package com.small2.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "picture")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
//图片文件
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String filename;

    @Column
    String username;

    @Column
    Timestamp timestamp;

    @Column
    int foldid;

    @Column
    boolean isPass;

    @Column
    String type;

    @Column
    long size;

    @Column
    String path;


}
