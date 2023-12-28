package com.example.flightscheduling;

import lombok.Getter;

public enum Airport {
    VCS("VCS", "Côn Đảo", "Bà Rịa - Vũng Tàu"),
    UIH("UIH", "Phù Cát", "Bình Định"),
    CAH("CAH", "Cà Mau", "Cà Mau"),
    VCA("VCA", "Cần Thơ", "Cần Thơ"),
    BMV("BMV", "Buôn Ma Thuột", "Đắk Lắk"),
    DAD("DAD", "Đà Nẵng", "Đà Nẵng"),
    DIN("DIN", "Điện Biên Phủ", "Điện Biên"),
    PXU("PXU", "Pleiku", "Gia Lai"),
    HPH("HPH", "Cát Bi", "Hải Phòng"),
    HAN("HAN", "Nội Bài", "Hà Nội"),
    SGN("SGN", "Tân Sơn Nhất", "Hồ Chí Minh");
    @Getter
    private final String IATACode;

    private final String name;
    private final String province;
    Airport(String IATACode, String name, String province) {
        this.IATACode = IATACode;
        this.name = name;
        this.province = province;
    }
}
