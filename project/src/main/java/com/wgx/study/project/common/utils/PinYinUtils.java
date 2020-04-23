package com.wgx.study.project.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * 该工具类是基于pinyin4J的简单封装
 * 参考：https://blog.csdn.net/xpeng_pjx/article/details/79810634
 * <p>
 * pinyin4J是一个可以将汉字转换成拼音的工具，非常实用，其提供的PinyinHelper静态类对外提供拼音转换的服务，主要用到以下两个方法：
 * static public String[] toHanyuPinyinStringArray(char ch) //将char(必须为汉字单字)转化为拼音，如果ch为非汉字，返回null
 * static public String[] toHanyuPinyinStringArray(char ch,HanyuPinyinOutputFormat outputFormat) //可以设置输出的格式
 */
public class PinYinUtils {

    //TODO str接受""，不接受null

    /**
     * 获取全拼
     *
     * @param str
     * @return
     */
    public static String getPingYin(String str, Boolean toUpperCase) {
        char[] chars = null;
        chars = str.toCharArray();
        String[] strings = new String[chars.length];
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder convert = new StringBuilder();
        try {
            for (char aChar : chars) {
                // 判断是否为汉字字符
                if (Character.toString(aChar).matches("[\\u4E00-\\u9FA5]+")) {
                    strings = PinyinHelper.toHanyuPinyinStringArray(aChar, format);
                    convert.append(strings[0]);
                } else {
                    convert.append(Character.toString(aChar));
                }
            }
            if (toUpperCase) {
                return convert.toString().toUpperCase();
            }
            return convert.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return convert.toString();
    }

    /**
     * 获取所有中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str, Boolean toUpperCase) {

        StringBuilder convert = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        if (toUpperCase) {
            return convert.toString().toUpperCase();
        }
        return convert.toString();
    }

    /**
     * 获取第一个中文首字母
     *
     * @param str
     * @return
     */

    public static String getHeadCharUpper(String str, Boolean toUpperCase) {
        String convert = getPinYinHeadChar(str, toUpperCase);
        if ("".equals(convert)){
            return "";
        }
        return String.valueOf(convert.charAt(0));
    }


    public static void main(String[] args) {

        String cnStr = "拼音转换";
        System.out.println("转换拼音 : " + getPingYin(cnStr,true));
        System.out.println("所有首字母 : " + getPinYinHeadChar(cnStr,true));
        System.out.println("第一个首字母 : " + getHeadCharUpper(cnStr,true));

    }

}