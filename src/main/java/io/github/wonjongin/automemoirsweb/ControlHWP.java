package io.github.wonjongin.automemoirsweb;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.charshape.*;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControlHWP {
    // HWP 파일 생성을 위한 함수
    public static void createHWP(String path) throws Exception {
        HWPFile hwpFile = BlankFileMaker.make();
        if (hwpFile != null) {
            HWPWriter.toFile(hwpFile, path);
        }
    }

    // HWP 파일에 쓰기 위한 함수
    public static void writeHWP(String path, String title, LocalDate date, String time, String place, String desc) throws Exception {
        String dateStr = date.getYear() + "년 " + date.getMonthValue() + "월 " + date.getDayOfMonth() + "일";
        HWPFile hwpFile = HWPReader.fromFile(path);
        String divisionLine = "________________________________________________________________________";
        if (hwpFile != null) {
            /**  문단과 내용매치표
             *   1. 타이틀
             *   2. 일시
             *   3. 시간
             *   4. 장소
             *   5. 나눔선
             *   6. 본문
             */
            // 섹션의 개수
            int amountSections = 6;

            // 섹션 생성
            for (int i = 0; i < amountSections - 1; i++) {
                hwpFile.getBodyText().addNewSection();
            }

            // 섹션 선언
            ArrayList<Section> sections = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                sections.add(hwpFile.getBodyText().getSectionList().get(i));
            }

            // 문단 추가
            for (int i = 1; i < amountSections; i++) {
                sections.get(i).addNewParagraph();
            }

            // 문단 선언
            ArrayList<Paragraph> paragraphs = new ArrayList<>();
            for (int i = 0; i < amountSections; i++) {
                paragraphs.add(sections.get(i).getParagraph(0));
            }

            // 문단에 글자 모양, 줄 모양, 글자란 추가
            for (int i = 1; i < amountSections; i++) {
                paragraphs.get(i).createCharShape();
                paragraphs.get(i).createLineSeg();
                paragraphs.get(i).createText();
            }

            // 들어갈 내용 선언
            // ArrayList<String> inputStrings = new ArrayList<>(List.of(title, "\n일시: " + dateStr, "시간: " + time, "장소: " + place, divisionLine, "\n" + desc));
            ArrayList<String> inputStrings = new ArrayList<>();
            inputStrings.add(title);
            inputStrings.add("\n일시: "+dateStr);
            inputStrings.add("시간: " + time);
            inputStrings.add("장소: " + place);
            inputStrings.add(divisionLine);
            inputStrings.add("\n" + desc);
            // 이게 9버전 이상에서 list.of가 가능 하다고 함 그래서 8버전에서 쓰게 만들어야함

            // 내용 문단에 넣기
            for (int i = 0; i < amountSections; i++) {
                paragraphs.get(i).getText().addString(inputStrings.get(i));
            }

            // 글자 모양 설정
            paragraphs.get(0).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 20, true));
            paragraphs.get(1).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 11, false));
            paragraphs.get(2).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 11, false));
            paragraphs.get(3).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 11, false));
            paragraphs.get(4).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 11, true));
            paragraphs.get(5).getCharShape().addParaCharShape(0, createCharShapeTemplate(hwpFile, 11, false));

            // 문단 모양 설정
            int[] centerParagraphs = {0, 4}; // 가운데 정렬할 문단의 인덱스들
            int[] leftParagraphs = {5}; // 왼쪽 정렬할 문단의 인덱스들
            int[] rightParagraphs = {1, 2, 3}; // 오른쪽 정렬할 문단의 인덱스들

            // 가운데 정렬
            for (int i = 0; i < centerParagraphs.length; i++) {
                ParaHeader ph = paragraphs.get(centerParagraphs[i]).getHeader();
                ParaShape paraShape = hwpFile.getDocInfo().addNewParaShape();
                paraShape.getProperty1().setAlignment(Alignment.Center);
                paraShape.setLineSpace(160);
                int paraShapeId = hwpFile.getDocInfo().getParaShapeList().size() - 1;
                ph.setParaShapeId(paraShapeId);
            }
            // 오른쪽 정렬
            for (int i = 0; i < rightParagraphs.length; i++) {
                ParaHeader ph = paragraphs.get(rightParagraphs[i]).getHeader();
                ParaShape paraShape = hwpFile.getDocInfo().addNewParaShape();
                paraShape.getProperty1().setAlignment(Alignment.Right);
                paraShape.setLineSpace(160);
                int paraShapeId = hwpFile.getDocInfo().getParaShapeList().size() - 1;
                ph.setParaShapeId(paraShapeId);
            }
            // 왼쪽 정렬
            for (int i = 0; i < leftParagraphs.length; i++) {
                ParaHeader ph = paragraphs.get(leftParagraphs[i]).getHeader();
                ParaShape paraShape = hwpFile.getDocInfo().addNewParaShape();
                paraShape.getProperty1().setAlignment(Alignment.Left);
                paraShape.setLineSpace(160);
                int paraShapeId = hwpFile.getDocInfo().getParaShapeList().size() - 1;
                ph.setParaShapeId(paraShapeId);
            }

            HWPWriter.toFile(hwpFile, path);
        }
    }

    public static void readPropHWP(String path) throws Exception {
        HWPFile hwpFile = HWPReader.fromFile(path);
        if (hwpFile != null) {
            // 개발, 테스트용 코드
        }

    }

    // 글자 모양 ID 를 얻기 위한 함수
    private static int createCharShapeTemplate(HWPFile hwpFile, int fontSize, boolean bold) {
        //        HWPFile hwpFile = new HWPFile();
        CharShape cs = hwpFile.getDocInfo().addNewCharShape();
        // 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
        cs.getFaceNameIds().setForAll(0);

        cs.getRatios().setForAll((short) 100);
        cs.getCharSpaces().setForAll((byte) 0);
        cs.getRelativeSizes().setForAll((short) 100);
        cs.getCharOffsets().setForAll((byte) 0);
        cs.setBaseSize((int) (fontSize * 100.0f));

        cs.getProperty().setItalic(false);
        cs.getProperty().setBold(bold);
        cs.getProperty().setUnderLineSort(UnderLineSort.None);
        cs.getProperty().setOutterLineSort(OutterLineSort.None);
        cs.getProperty().setShadowSort(ShadowSort.None);
        cs.getProperty().setEmboss(false);
        cs.getProperty().setEngrave(false);
        cs.getProperty().setSuperScript(false);
        cs.getProperty().setSubScript(false);
        cs.getProperty().setStrikeLine(false);
        cs.getProperty().setEmphasisSort(EmphasisSort.None);
        cs.getProperty().setUsingSpaceAppropriateForFont(false);
        cs.getProperty().setStrikeLineShape(BorderType.None);
        cs.getProperty().setKerning(false);

        cs.setShadowGap1((byte) 0);
        cs.setShadowGap2((byte) 0);
        cs.getCharColor().setValue(0x00000000);
        cs.getUnderLineColor().setValue(0x00000000);
        cs.getShadeColor().setValue(-1);
        cs.getShadowColor().setValue(0x00b2b2b2);
        cs.setBorderFillId(0);

        return hwpFile.getDocInfo().getCharShapeList().size() - 1;
    }
}
