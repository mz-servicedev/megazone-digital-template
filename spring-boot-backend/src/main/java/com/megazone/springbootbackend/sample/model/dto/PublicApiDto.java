package com.megazone.springbootbackend.sample.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : </li>
 * <li>서브 업무명 : </li>
 * <li>파  일  명 : SpecialDayDTO</li>
 * <li>작  성  자 : mz01-ohyunbk</li>
 * <li>작  성  일 : 2023/02/20</li>
 * <li>설     명 : </li>
 * </ul>
 * <pre>
 * ======================================
 * 작성자             일시                  내용
 * mz01-ohyunbk    2023/02/20 9:32 AM    최초 생성
 * ======================================
 * </pre>
 ***************************************************/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PublicApiDto {

  private Response response;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class Response {

    private Header header;
    private Body body;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Header {

      private String resultCode;
      private String resultMsg;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Body {

      private Items items;
      private int numOfRows;
      private int pageNo;
      private int totalCount;

      @Getter
      @NoArgsConstructor
      @AllArgsConstructor
      @ToString
      public static class Items {

        private List<Item> item;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @ToString
        public static class Item {

          private String dateKind;
          private String dateName;
          private String isHoliday;
          private int locdate;
          private int seq;
        }
      }
    }
  }
}
