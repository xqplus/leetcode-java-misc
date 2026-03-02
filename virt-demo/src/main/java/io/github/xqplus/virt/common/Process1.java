package io.github.xqplus.virt.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Process1 {

    public static void main(String[] args) throws IOException {
        System.out.println(Integer.toBinaryString(-1 ^ 1));
    }

    public static String format(double size, RoundingMode roundingMode) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(size);
    }
}
