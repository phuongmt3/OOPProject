# OOPProject
 BTL_Bomberman
 
 Mô phỏng game Bomberman và phát triển thêm các loại enemy khác nhau với thuật toán tìm đường khác nhau, BomberAI có thể tự chơi.
 
 BomberAI mà không AI lắm, cơ mà thú vị: 
 - Tạo một mapAI phụ, với những chỉ số thể hiện ô có thể đi, ô nên đi để ăn item, ô không thể đi/không nên đi, ô nên đặt bom... dự đoán trong vòng 1 bước đi tới, phủ lên map chính để bomber xác định đường nên đi, hành động cần thực hiện.
 - Mỗi lần bomber đi được 1 ô: update mapAI -> choose safe way -> 
 - //-1 can't go; -2 enemy pos; -3 flame can be there; 0 safe; 1 enemy will come need to avoid; 2 should put bomb
