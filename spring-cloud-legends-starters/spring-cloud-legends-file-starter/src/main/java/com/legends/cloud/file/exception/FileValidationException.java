package com.legends.cloud.file.exception;

/**
 * @Author herion
 * @Description
 * @Date 11:05 2019/9/4
 * @Param 
 * @return 
 **/
public class FileValidationException extends RuntimeException {

    private static final long serialVersionUID = -8143229915907803343L;
    private String code;
    private String msg;

    public FileValidationException(final String code) {
        initException(code);
    }

    public FileValidationException(final String code, final String msg) {

        super(msg);
        this.code = code;
        this.msg = msg;
        initException(code);
    }

    public FileValidationException(final String code, final Throwable throwable) {
        super(throwable);
        initException(code);
        this.code = code;
        this.msg = throwable.getMessage();
    }

    public String getCode() {
        final Throwable cause = this.getCause();
        if (cause != null && (cause instanceof FileValidationException)) {
            return ((FileValidationException) cause).getCode();
        }
        return this.code;
    }

    private void initException(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
