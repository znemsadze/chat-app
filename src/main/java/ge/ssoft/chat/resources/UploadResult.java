package ge.ssoft.chat.resources;

/**
 * Created by zviad on 10/30/16.
 */
public class UploadResult {
    private String errorCodde;
    private String message;
    private String fileName;

    public UploadResult(String errorCodde, String message, String fileName) {
        this.errorCodde = errorCodde;
        this.message = message;
        this.fileName = fileName;
    }

    public UploadResult() {
    }

    public UploadResult(String errorCodde) {
        this.errorCodde = errorCodde;
    }

    public UploadResult(String errorCodde, String message) {
        this.errorCodde = errorCodde;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getErrorCodde() {
        return errorCodde;
    }

    public void setErrorCodde(String errorCodde) {
        this.errorCodde = errorCodde;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
