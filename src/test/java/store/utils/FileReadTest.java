package store.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReadTest {

    @Test
    void 파일_읽기_테스트() throws IOException {
        //given
        File tempFile = getTempFile();

        //when
        List<String> result = FileRead.readFile(tempFile.getAbsolutePath());

        //then
        assertThat(result.get(0)).isEqualTo("콜라,1000,10,탄산2+1");
        assertThat(result.get(1)).isEqualTo("사이다,1200,5,null");
        assertThat(result.get(2)).isEqualTo("오렌지주스,1800,8,MD추천상품");
        assertThat(result.size()).isEqualTo(3);

        tempFile.delete();
    }

    private static File getTempFile() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("name,price,quantity,promotion\n");
        writer.write("콜라,1000,10,탄산2+1\n");
        writer.write("사이다,1200,5,null\n");
        writer.write("오렌지주스,1800,8,MD추천상품\n");
        writer.close();
        return tempFile;
    }
}