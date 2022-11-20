package cn.edu.lzu.yhyang20;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KumoTest {
    public static void main(String[] args) throws IOException, NullPointerException {
        final var r = KumoTest.class.getResource("");
        if (r == null) return;
        final var path = r.getPath();
        final var f = new File(path + "assets/20.txt");// 定位20大报告文件
        final var analyzer = new FrequencyAnalyzer();
        analyzer.setWordFrequenciesToReturn(10000);
        analyzer.setMinWordLength(2);// 过滤长度小于2的词语
        analyzer.setWordTokenizer(new ChineseWordTokenizer());
        /************/
        final var words = analyzer.load(f);
        final var builder = new StringBuilder();
        words.forEach(w -> builder.append(w.getWord()).append(" ").append(w.getFrequency()).append("\n"));
        final var collected = builder.toString();
        final var txt = new File("d:/kumo.txt");
        if (txt.exists()) txt.delete();
        try {
            txt.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final var writer = new FileWriter(txt, true);
            writer.write(collected);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /************/ //词频写入 D:/kumo.txt
        final var font = new Font("STSong-Light", Font.PLAIN, 18);
        final var dim = new Dimension(500, 500);
        final var cloud = new WordCloud(dim, CollisionMode.PIXEL_PERFECT);
        cloud.setPadding(2);
        cloud.setBackground(new CircleBackground(255));
        cloud.setFontScalar(new SqrtFontScalar(12, 42));
        cloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        cloud.setKumoFont(new KumoFont(font));
        cloud.setBackgroundColor(new Color(255, 255, 255));
        cloud.setBackground(new CircleBackground(255));
        cloud.build(words);
        cloud.writeToFile("d:/kumo.png");// 词云结果输出到 D:/kumo.png
    }
}
