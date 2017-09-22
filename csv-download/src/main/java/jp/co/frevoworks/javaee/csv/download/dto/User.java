package jp.co.frevoworks.javaee.csv.download.dto;

import jp.co.frevoworks.javaee.csv.download.annotation.CsvColumn;
import lombok.Data;

/**
 * ユーザ情報
 */
@Data
public class User {
    @CsvColumn(header = "氏名")
    private String userName;
    
    @CsvColumn(header = "年齢")
    private int age;
}
