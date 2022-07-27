package uz.oliymahad.oliymahadquroncourse.exception;

public class FileManagementException extends APIException {

    private static final String FILE_UPLOAD_ERROR_ENG = "File upload error";

    private static final String FILE_DELETION_ERROR_ENG = "File could not deleted";

    public FileManagementException(String message) {
        super(message);
    }

    public static FileManagementException upload(){
        return new FileManagementException(FILE_UPLOAD_ERROR_ENG);
    }

    public static FileManagementException deletion(){
        return new FileManagementException(FILE_DELETION_ERROR_ENG);
    }

}
