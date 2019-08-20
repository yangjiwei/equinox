package com.equinox.util.exception;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 消息资源文件辅助工具类
 * <p/>
 * <p/>
 * 读取classpath下面的所有message_*.xml文件中的错误消息。
 *
 * @author liufengyu
 */

public final class MessageHelper {
    //private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);
    private static MessageHelper instance = new MessageHelper();

    private List<String> fileList = null;
    private Properties sysProps = null;

    private MessageHelper() {
        fileList = new ArrayList<String>();
        sysProps = new Properties();

        this.init();
    }

    public static MessageHelper getInstance() {
        return instance;
    }

    private void init() {
//		this.addMessageFile("message_app-core.xml");
    }

    /**
     * 添加错误消息文件（错误消息文件必须在classpath中，或在jar包中）
     *
     * @param fileName 文件名，如："message_app-core.xml"
     * @author liufengyu
     * @date 2011-9-9
     */
    public void addMessageFile(String fileName) {
        if (this.fileList.contains(fileName)) {
            return;
        }
        this.fileList.add(fileName);
        try {
            loadMessage(MessageHelper.class.getResourceAsStream("/" + fileName));
            //logger.info("加载错误消息文件{}", fileName);
        } catch (IOException e) {
            throw new ProgramException(e);
        } catch (Exception e) {
            throw new ProgramException(MessageFormat.format("消息文件{0}格式不正确。", fileName), e);
        }
    }

    /**
     * 从资源文件中加载错误消息到内存中
     */
    private void loadMessage(InputStream is) throws Exception {
        try {
            MessageParserHandler xmlHandler = new MessageParserHandler();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(is, xmlHandler);

            sysProps.putAll(xmlHandler.getProps());

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // 忽略
                    //logger.warn(e.getMessage());
                }
            }
        }
    }

    /**
     * 错误消息xml文件parser
     *
     * @author liufengyu
     */
    static class MessageParserHandler extends DefaultHandler {
        private Properties messages = null;
        private StringBuffer curValue = new StringBuffer();
        private String curKey = null;

        public Properties getProps() {
            return messages;
        }

        public void startDocument() {
            messages = new Properties();
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            curValue.setLength(0);
            if (attributes.getLength() > 0) {
                curKey = attributes.getValue(0);
            } else {
                curKey = null;
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            curValue.append(ch, start, length);
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            String txtVal = curValue.toString().trim();
            if (qName.equals("message")) {
                messages.put(curKey, txtVal);
            }
        }
    }

    /**
     * 根据errorCode取得一个错误消息，如果没有对应的错误消息，则返回errorCode本身。
     *
     * @param errorCode 错误码
     * @return 如果有错误消息，则返回错误消息，否则，返回errorCode
     */
    public String getMessage(String errorCode) {
        // 检测资源文件是否更新了
        String val = null;
        val = sysProps.getProperty(errorCode);

        if (val == null) {
            //logger.warn(MessageFormat.format("错误码{0}没有对应的错误消息。", errorCode));
            return errorCode;
        } else {
            return val;
        }
    }

    /**
     * 取得格式化后的消息。
     *
     * @param errorCode 错误码
     * @param args      参数
     * @return
     */
    public String getFormatedMessage(String errorCode, Object... args) {
        return "[" + errorCode + "]" + MessageFormat.format(this.getMessage(errorCode), args);
    }

    /**
     * 取得格式化后的消息。
     *
     * @param errorCode 错误码
     * @param args      参数
     * @return
     */
    public String getFormatedMessageNoCode(String errorCode, Object... args) {
        return MessageFormat.format(this.getMessage(errorCode), args);
    }

}
