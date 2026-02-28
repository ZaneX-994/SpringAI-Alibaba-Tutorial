package com.bytewizard.records;

/*
* @Description: jdk14以后的新特性, 记录类Record = Entity + lombok
* */
public record StudentRecord(String id, String name, String major, String email) {
}
