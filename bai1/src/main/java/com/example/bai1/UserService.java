package com.example.bai1;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ChatModel chatModel;

    public UserDTO getInfor(String email) {
        BeanOutputConverter<UserDTO> converter = new BeanOutputConverter<>(UserDTO.class);
        String template = """
                VAI TRÒ:
                Bạn là một hệ thống AI chuyên trích xuất thông tin có cấu trúc từ nội dung email.
                
                MỤC TIÊU:
                Phân tích email được cung cấp và trích xuất chính xác:
                - Tên khách hàng.
                - Số điện thoại của khách hàng.
                
                NGỮ CẢNH:
                Email cần phân tích được cung cấp dưới dạng:
                {email}
                
                NHIỆM VỤ:
                1. Đọc và phân tích toàn bộ nội dung email.
                2. Xác định tên khách hàng dựa trên thông tin xuất hiện trong email.
                3. Xác định số điện thoại của khách hàng dựa trên thông tin xuất hiện trong email.
                4. Chỉ sử dụng thông tin thực sự xuất hiện trong email.
                5. Không được tự suy đoán hoặc tự tạo dữ liệu nếu thông tin không tồn tại.
                
                RÀNG BUỘC NGHIÊM NGẶT:
                - Chỉ trả về một JSON object duy nhất.
                - Không được trả về Markdown.
                - Không được sử dụng code fence như ```json.
                - Không được thêm lời giải thích, nhận xét, lời chào hoặc bất kỳ nội dung nào ngoài JSON.
                - Không được thêm text trước hoặc sau JSON.
                - JSON phải hợp lệ và có thể parse trực tiếp bằng JSON parser.
                - Không được tự suy đoán thông tin bị thiếu.
                - Nếu không tìm thấy tên khách hàng, trả về giá trị null.
                - Nếu không tìm thấy số điện thoại, trả về giá trị null.
                - Giữ nguyên thông tin được trích xuất, không tự ý thay đổi nội dung.
                
                ĐỊNH DẠNG ĐẦU RA:
                - Chỉ trả về định dạng JSON sau: {format}
                """;

        Prompt prompt = new PromptTemplate(template)
                .create(Map.of("email", email, "format", converter.getFormat()));

        String response = chatModel.call(prompt).getResult().getOutput().getText();
        return converter.convert(response);
    }
}
