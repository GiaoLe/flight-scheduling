package com.example.flightscheduling;

import javafx.geometry.Point2D;
import lombok.Getter;

public enum Airport {
//    VCS("VCS", "Côn Đảo", "Bà Rịa - Vũng Tàu"),
//    UIH("UIH", "Phù Cát", "Bình Định"),
//    CAH("CAH", "Cà Mau", "Cà Mau"),
//    VCA("VCA", "Cần Thơ", "Cần Thơ"),
    DAD("DAD", "Đà Nẵng", "Đà Nẵng", new Point2D(320, 400)),
    BMV("BMV", "Buôn Ma Thuột", "Đắk Lắk", new Point2D(317, 570)),
//    DIN("DIN", "Điện Biên Phủ", "Điện Biên"),
//    PXU("PXU", "Pleiku", "Gia Lai"),
    HPH("HPH", "Cát Bi", "Hải Phòng", new Point2D(245, 160)),
    HAN("HAN", "Nội Bài", "Hà Nội", new Point2D(200, 150)),
    SGN("SGN", "Tân Sơn Nhất", "Hồ Chí Minh", new Point2D(240, 670));
    @Getter
    private final String IATACode;

    private final String name;
    private final String province;

    @Getter
    private final Point2D position;
    Airport(String IATACode, String name, String province, Point2D position) {
        this.IATACode = IATACode;
        this.name = name;
        this.province = province;
        this.position = position;
    }
}
