package uz.oliymahad.oliymahadquroncourse.service.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageProvider<R> extends Service{

    R pageOf(Pageable pageable);

}
