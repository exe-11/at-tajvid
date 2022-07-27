package uz.oliymahad.oliymahadquroncourse.service.core;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ResourcePage<T>  {

    private int totalPage;

    private long totalElement;

    private int number;

    private int size;

    private long numberOfElements;

    private List<T> content;

    private boolean hasContent;

    private boolean isFirst;

    private boolean isLast;

    private boolean hasNext;

    private boolean hasPrevious;

}
