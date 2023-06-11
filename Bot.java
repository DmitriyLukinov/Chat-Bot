package tutorial;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.vdurmont.emoji.EmojiParser;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private XSSFWorkbook workbook;
    public Bot() {
        this.workbook = new XSSFWorkbook(); // инициализируем экземпляр XSSFWorkbook в конструкторе
    }

    @Override
    public String getBotUsername() {return "Knopka333_bot";}

    @Override
    public String getBotToken() {
        return "5704161442:AAE51voX_WRk6nQSLYHGUsnZHf6-OPlfoXI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
            var callbackQuery = update.getCallbackQuery();
            var data = callbackQuery.getData();
            var chatId = callbackQuery.getMessage().getChatId();

            if(data.equals("dnepr")){
                sendMenuCafe(callbackQuery.getFrom().getId(), message_text_City);
                Del(chatId.toString(),counter-1);
                order[0] = "Днепр";
            }
            if(data.equals("ternopol")){
                sendMenuCafe(callbackQuery.getFrom().getId(), message_text_City);
                Del(chatId.toString(),counter-1);
                order[0] = "Тернополь";
            }
            if(data.equals("khmelnitskiy")){
                sendMenuCafe(callbackQuery.getFrom().getId(), message_text_City);
                Del(chatId.toString(),counter-1);
                order[0] = "Хмельницкий";
            }
            if(data.equals("backtosendMenuCity")){
                sendMenuCity(callbackQuery.getFrom().getId(), message_text_sendMenuCity);
                Del(chatId.toString(),counter-1);
            }
            /////////////////////////////
            if(data.equals("cafe1")){
                sendMenuTable(callbackQuery.getFrom().getId(),message_text_Cafe);
                Del(chatId.toString(),counter-1);
                order[1] = "Кафе 1";
            }
            if(data.equals("cafe2")){
                sendMenuTable(callbackQuery.getFrom().getId(),message_text_Cafe);
                Del(chatId.toString(),counter-1);
                order[1] = "Кафе 2";
            }
            if(data.equals("cafe3")){
                sendMenuTable(callbackQuery.getFrom().getId(),message_text_Cafe);
                Del(chatId.toString(),counter-1);
                order[1] = "Кафе 3";
            }
            if(data.equals("cafe4")){
                sendMenuTable(callbackQuery.getFrom().getId(),message_text_Cafe);
                Del(chatId.toString(),counter-1);
                order[1] = "Кафе 4";
            }
            if(data.equals("backtosendMenuCafe")){
                sendMenuCafe(callbackQuery.getFrom().getId(), message_text_City);
                Del(chatId.toString(),counter-1);
            }
            ////////////////////////////////////
            if(data.equals("table1")){
                sendMenuTime(callbackQuery.getFrom().getId(), message_text_Time);
                Del(chatId.toString(),counter-1);
                order[2] = "Столик 1";
            }
            if(data.equals("table2")){
                sendMenuTime(callbackQuery.getFrom().getId(), message_text_Time);
                Del(chatId.toString(),counter-1);
                order[2] = "Столик 2";
            }
            if(data.equals("table3")){
                sendMenuTime(callbackQuery.getFrom().getId(), message_text_Time);
                Del(chatId.toString(),counter-1);
                order[2] = "Столик 3";
            }
            if(data.equals("backtosendMenuTable")){
                sendMenuTable(callbackQuery.getFrom().getId(), message_text_Cafe);
                Del(chatId.toString(),counter-1);
            }
            ////////////////////////////////////////
            if(data.equals("time1")){
                order[3] = "10:00";
                sendMenuConfirm(callbackQuery.getFrom().getId());
                Del(chatId.toString(),counter-1);
            }
            if(data.equals("time2")){
                order[3] = "13:00";
                sendMenuConfirm(callbackQuery.getFrom().getId());
                Del(chatId.toString(),counter-1);
            }
            if(data.equals("time3")){
                order[3] = "17:00";
                sendMenuConfirm(callbackQuery.getFrom().getId());
                Del(chatId.toString(),counter-1);
            }
            ////////////////////////////////////////
            if(data.equals("confirm")){
                sendText(callbackQuery.getFrom().getId(), order);
                Del(chatId.toString(),counter-1);

                int unixTime = callbackQuery.getMessage().getDate(); //Getting the time the message was sent
                Date date = new java.util.Date(unixTime*1000L); //Converting to date format
                String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date); //Formatting the date
                System.out.println("The message was sent at: " + formattedDate);
                writeToExel(callbackQuery.getFrom().getUserName(), formattedDate);
            }
            if(data.equals("backtosendMenuTime")){
                sendMenuTime(callbackQuery.getFrom().getId(), message_text_Time);
                Del(chatId.toString(),counter-1);
            }
        }
        // обработка обычных сообщений
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();
        String smth = msg.getText();
        counter = update.getMessage().getMessageId();

        sendMenuCity(id, message_text_sendMenuCity);
        System.out.println(counter);
    }
    public void sendMenuCity(Long who, String txt){
        InlineKeyboardButton dnepr = new InlineKeyboardButton( dnepr_emoji+ "Дніпро");
        dnepr.setCallbackData("dnepr");
        InlineKeyboardButton ternopol = new InlineKeyboardButton(ternopol_emoji +"Тернопільська");
        ternopol.setCallbackData("ternopol");
        InlineKeyboardButton khmelnitskaya = new InlineKeyboardButton(khmelnitskiy_emoji +"Хмельницька");
        khmelnitskaya.setCallbackData("khmelnitskiy");

        InlineKeyboardMarkup keyboardM1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(dnepr);
        keyboardButtonsRow1.add(ternopol);
        keyboardButtonsRow2.add(khmelnitskaya);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        keyboardM1.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setParseMode("HTML");
        sm.setText("<b>"+txt+"</b>");
        sm.setReplyMarkup(keyboardM1);

        counter++;
        System.out.println("menu city" + counter);

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMenuCafe(Long who, String txt){
        InlineKeyboardButton cafe1 = new InlineKeyboardButton(cafe1_emoji + "Кафе 1" );
        cafe1.setCallbackData("cafe1");
        InlineKeyboardButton cafe2 = new InlineKeyboardButton(cafe2_emoji + "Кафе 2");
        cafe2.setCallbackData("cafe2");
        InlineKeyboardButton cafe3 = new InlineKeyboardButton(cafe3_emoji + "Кафе 3");
        cafe3.setCallbackData("cafe3");
        InlineKeyboardButton cafe4 = new InlineKeyboardButton(cafe4_emoji + "Кафе 4");
        cafe4.setCallbackData("cafe4");
        InlineKeyboardButton backtosendMenuCity = new InlineKeyboardButton(back_emoji + "Назад");
        backtosendMenuCity.setCallbackData("backtosendMenuCity");

        InlineKeyboardMarkup keyboardM1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow1.add(cafe1);
        keyboardButtonsRow1.add(cafe2);
        keyboardButtonsRow2.add(cafe3);
        keyboardButtonsRow2.add(cafe4);
        keyboardButtonsRow3.add(backtosendMenuCity);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        keyboardM1.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setParseMode("HTML");
        sm.setText("<b>"+txt+"</b>");
        sm.setReplyMarkup(keyboardM1);

        counter++;
        System.out.println("menu dnepr" + counter);

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMenuTable(Long who, String txt){
        InlineKeyboardButton table1 = new InlineKeyboardButton(table1_emoji + "Столик 1" );
        table1.setCallbackData("table1");
        InlineKeyboardButton table2 = new InlineKeyboardButton(table2_emoji + "Столик 2" );
        table2.setCallbackData("table2");
        InlineKeyboardButton table3 = new InlineKeyboardButton(table3_emoji + "Столик 3" );
        table3.setCallbackData("table3");
        InlineKeyboardButton backtosendMenuCafe = new InlineKeyboardButton(back_emoji + "Назад");
        backtosendMenuCafe.setCallbackData("backtosendMenuCafe");

        InlineKeyboardMarkup keyboardM1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(table1);
        keyboardButtonsRow1.add(table2);
        keyboardButtonsRow1.add(table3);
        keyboardButtonsRow2.add(backtosendMenuCafe);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        keyboardM1.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setParseMode("HTML");
        sm.setText("<b>"+txt+"</b>");
        sm.setReplyMarkup(keyboardM1);

        counter++;
        //System.out.println("menu dnepr" + counter);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMenuTime(Long who, String txt){
        InlineKeyboardButton time1 = new InlineKeyboardButton(time1_emoji + "10:00" );
        time1.setCallbackData("time1");
        InlineKeyboardButton time2 = new InlineKeyboardButton(time2_emoji + "13:00" );
        time2.setCallbackData("time2");
        InlineKeyboardButton time3 = new InlineKeyboardButton(time3_emoji + "17:00" );
        time3.setCallbackData("time3");
        InlineKeyboardButton backtosendMenuTable = new InlineKeyboardButton(back_emoji + "Назад");
        backtosendMenuTable.setCallbackData("backtosendMenuTable");

        InlineKeyboardMarkup keyboardM1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(time1);
        keyboardButtonsRow1.add(time2);
        keyboardButtonsRow1.add(time3);
        keyboardButtonsRow2.add(backtosendMenuTable);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        keyboardM1.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setParseMode("HTML");
        sm.setText("<b>"+txt+"</b>");
        sm.setReplyMarkup(keyboardM1);

        counter++;
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMenuConfirm(Long who){
        InlineKeyboardButton confirm = new InlineKeyboardButton("Правильно?" );
        confirm.setCallbackData("confirm");
        InlineKeyboardButton backtosendMenuTime = new InlineKeyboardButton(back_emoji + "Назад");
        backtosendMenuTime.setCallbackData("backtosendMenuTime");

        InlineKeyboardMarkup keyboardM1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(confirm);
        keyboardButtonsRow2.add(backtosendMenuTime);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        keyboardM1.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setParseMode("HTML");
        sm.setText("<b>"+"Ваш заказ: " + order[0] + ", " + order[1] + ", " + order[2] + ", " +  order[3] +"</b>");
        sm.setReplyMarkup(keyboardM1);

        counter++;
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void Del(String chatID, Integer messageID ){
        DeleteMessage dm = new DeleteMessage();
        dm.setChatId(chatID);
        dm.setMessageId(messageID);
        try {
            execute(dm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
    public void writeToExel(String who, String date){
        XSSFSheet sheet = workbook.getSheet("Employee Data");
        if (sheet == null) {
            sheet = workbook.createSheet("Employee Data");
        }

        Row row = sheet.createRow(counterExel);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(date);
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(who);
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(order[0]);
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(order[1]);
        Cell cell4 = row.createCell(4);
        cell4.setCellValue(order[2]);
        Cell cell5 = row.createCell(5);
        cell5.setCellValue(order[3]);
        counterExel = counterExel + 1;
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("D:\\Desktop\\Exp.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void sendText(Long who, String[] order){
        counter++;
        String showOrder = "Ваш заказ: " + order[0] + ", " + order[1] + ", " + order[2] + ", " + order[3];
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(showOrder).build();    //Message content
        Message message;
        try {
            message = execute(sm);                        //Saving the sent message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    //emojis for sendMenuCity
    private String smile_emoji = EmojiParser.parseToUnicode(":sparkles:");
    private String sparkle_emoji = EmojiParser.parseToUnicode(":sparkles:");
    private String dnepr_emoji = EmojiParser.parseToUnicode(":department_store:");
    private String ternopol_emoji = EmojiParser.parseToUnicode(":european_castle:");
    private String khmelnitskiy_emoji = EmojiParser.parseToUnicode(":house_with_garden:");
    private String smth_elese_emoji = EmojiParser.parseToUnicode(":globe_with_meridians:");
    //emojis for sendMenuCafe
    private String cafe1_emoji = EmojiParser.parseToUnicode(":zap:");
    private String cafe2_emoji = EmojiParser.parseToUnicode(":knife:");
    private String cafe3_emoji = EmojiParser.parseToUnicode(":dizzy:");
    private String cafe4_emoji = EmojiParser.parseToUnicode(":heartbeat:");
    private String back_emoji = EmojiParser.parseToUnicode(":back:");
    private String select_emoji = EmojiParser.parseToUnicode(":eye:");

    //emojis for sendMenuTable
    private String table1_emoji = EmojiParser.parseToUnicode(":fish:");
    private String table2_emoji = EmojiParser.parseToUnicode(":ramen:");
    private String table3_emoji = EmojiParser.parseToUnicode(":icecream:");

    //emojis for sendMenuTime
    private String time1_emoji = EmojiParser.parseToUnicode(":clock10:");
    private String time2_emoji = EmojiParser.parseToUnicode(":clock1:");
    private String time3_emoji = EmojiParser.parseToUnicode(":clock5:");
    private String time_emoji = EmojiParser.parseToUnicode(":timer_clock:");

    public String message_text_sendMenuCity =
            dnepr_emoji + "Добро пожаловать " + sparkle_emoji+ "В наши кафе" + sparkle_emoji +
                    "\n\nВаш #167618\n\nВыберите место";
    public String message_text_City = select_emoji + "Выберите кафе";
    public String message_text_Cafe = cafe1_emoji + "Выберите столик";
    public String message_text_Time = time_emoji + "Выберите время";
    private Integer counter;
    private static int counterExel = 0;
    String[] order = new String[5];
}
