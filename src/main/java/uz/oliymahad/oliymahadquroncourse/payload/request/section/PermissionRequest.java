package uz.oliymahad.oliymahadquroncourse.payload.request.section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionRequest {
    private boolean visibility;
    private boolean update;
    private boolean delete;
    private boolean info;

//    public boolean getVisibility() {
//        return visibility;
//    }
//
//    public void setVisibility(boolean visibility) {
//        this.visibility = visibility;
//    }
//
//    public boolean getDelete() {
//        return delete;
//    }
//
//    public void setDelete(boolean delete) {
//        this.delete = delete;
//    }
//
//    public boolean getUpdate() {
//        return update;
//    }
//
//    public void setUpdate(boolean update) {
//        this.update = update;
//    }
//
//    public boolean getInfo() {
//        return info;
//    }
//
//    public void setInfo(boolean info) {
//        this.info = info;
//    }
}
