package AI;
import model.Board;
import model.BoardHelper;
import model.Piece;

public class StaticEvaluator implements Evaluator {

    //hàm đánh giá
    public int eval(Piece[][] board , int player){
        int mob = evalMobility(board,player);
        int sc = evalDiscDiff(board,player);
        return 2*mob + sc + 1000*evalCorner(board,player);
    }

    //hàm đánh giá theo số quân cờ: Điều này sẽ tạo ra một giá trị đánh giá trong khoảng từ -100 đến 100,
    // với giá trị dương cho thấy lợi thế của người chơi, giá trị âm cho thấy lợi thế của đối thủ,
    // và giá trị gần với 0 cho thấy sự cân bằng.
    /*Lượng quân cờ của người chơi và đối thủ: Hàm đánh giá tính toán số lượng quân cờ mà người chơi
    và đối thủ đang kiểm soát trên bàn cờ. Việc này giúp đánh giá mức độ chiếm ưu thế của mỗi bên.
    Hiệu số quân cờ và tổng số quân cờ trên bàn cờ:
    Bằng cách lấy hiệu số quân cờ của người chơi và đối thủ,
     chúng ta đo lường sự khác biệt giữa số quân cờ của họ.
     Sau đó, chia cho tổng số quân cờ trên bàn cờ giúp chuẩn hóa giá trị đánh giá.*/
    public static int evalDiscDiff(Piece[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int mySC = BoardHelper.getPlayerStoneCount(board,player);
        int opSC = BoardHelper.getPlayerStoneCount(board,oplayer);

        return 100 * (mySC - opSC) / (mySC + opSC);
    }

    //hàm đánh giá theo số lượng quân có thể đặt:
    // Hàm đánh giá tính toán số lượng vị trí trống mà người chơi và đối thủ có thể đặt các quân cờ
    // trên bàn cờ. Điều này giúp đánh giá khả năng tấn công và phòng thủ của mỗi bên.
    //
    //Hiệu số vị trí có thể đặt và tổng số ô trống trên bàn cờ:
    // Bằng cách lấy hiệu số vị trí có thể đặt của người chơi và đối thủ,
    // chúng ta đo lường sự khác biệt trong khả năng đi lại giữa hai bên.
    // Sau đó, chia cho tổng số ô trống trên bàn cờ giúp chuẩn hóa giá trị đánh giá.
    //
    //Phạm vi giá trị đánh giá: Kết quả của hàm đánh giá nằm trong khoảng từ -100 đến 100.
    // Giá trị dương cho thấy người chơi có nhiều vị trí hơn để đặt quân cờ hơn đối thủ,
    // giá trị âm cho thấy ngược lại, và giá trị gần với 0 cho thấy
    // sự cân bằng trong số lượng vị trí có thể đặt giữa hai bên.
    //Như vậy, hàm đánh giá này cung cấp một cách đo lường tương đối khả năng tạo ra các nước đi
    // tiềm năng của mỗi bên và so sánh chúng với nhau trong trò chơi Othello.
    public static int evalMobility(Piece[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myMoveCount = BoardHelper.getPointMove(board,player).size();
        int opMoveCount = BoardHelper.getPointMove(board,oplayer).size();

        return 100 * (myMoveCount - opMoveCount) / (myMoveCount + opMoveCount + 1);
    }


    //hàm đánh giá vị trí đặt quân cờ:
    /*đánh giá khả năng chiếm các ô góc của bàn cờ trong trò chơi Othello. Dưới đây là ý nghĩa của hàm này:
    Khả năng chiếm các ô góc của người chơi và đối thủ: Hàm này đếm số lượng ô góc mà người chơi
    và đối thủ đang kiểm soát trên bàn cờ. Các ô góc này có giá trị chiến lược cao
    trong Othello vì chúng thường khó bị chiếm giữ lại và đảm bảo rằng các quân cờ trong
     các ô góc này sẽ không bị lật lại.
     Hiệu số ô góc và tổng số ô góc trên bàn cờ:
     Bằng cách lấy hiệu số ô góc của người chơi và đối thủ,
     chúng ta đo lường sự khác biệt trong khả năng chiếm góc của hai bên.
     Sau đó, chia cho tổng số ô góc trên bàn cờ cộng thêm 1 để tránh trường hợp tổng số ô góc là 0,
     giúp chuẩn hóa giá trị đánh giá.

Phạm vi giá trị đánh giá: Kết quả của hàm đánh giá nằm trong khoảng từ -100 đến 100. Giá trị dương cho thấy người chơi chiếm nhiều ô góc hơn đối thủ, giá trị âm cho thấy ngược lại, và giá trị gần với 0 cho thấy sự cân bằng trong khả năng chiếm góc giữa hai bên.*/
    public static int evalCorner(Piece[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myCorners = 0;
        int opCorners = 0;

        if(board[0][0].getValue()==player) myCorners++;
        if(board[7][0].getValue()==player) myCorners++;
        if(board[0][7].getValue()==player) myCorners++;
        if(board[7][7].getValue()==player) myCorners++;

        if(board[0][0].getValue()==oplayer) opCorners++;
        if(board[7][0].getValue()==oplayer) opCorners++;
        if(board[0][7].getValue()==oplayer) opCorners++;
        if(board[7][7].getValue()==oplayer) opCorners++;

        return 100 * (myCorners - opCorners) / (myCorners + opCorners + 1);
    }

    //ham danh gia theo trong so cua vi tri
    public static int evalBoardMap(Piece[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;
        int[][] W = {
                {200 , -100, 100,  50,  50, 100, -100,  200},
                {-100, -200, -50, -50, -50, -50, -200, -100},
                {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                {-100, -200, -50, -50, -50, -50, -200, -100},
                {200 , -100, 100,  50,  50, 100, -100,  200}};

        //if corners are taken W for that 1/4 loses effect
        //neu quan co duoc dat o cac goc th 1/4 ban co mat di hieu luc
        if(board[0][0].getValue() != 0){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[0][7].getValue() != 0){
            for (int i = 0; i < 3; i++) {
                for (int j = 4; j <= 7; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[7][0].getValue() != 0){
            for (int i = 5; i < 8; i++) {
                for (int j = 0; j <= 3; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[7][7].getValue() != 0){
            for (int i = 5; i < 8; i++) {
                for (int j = 4; j <= 7; j++) {
                    W[i][j] = 0;
                }
            }
        }


        int myW = 0;
        int opW = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j].getValue()==player) myW += W[i][j];
                if(board[i][j].getValue()==oplayer) opW += W[i][j];
            }
        }

        return (myW - opW) / (myW + opW + 1);
    }

    public static int evalParity(Piece[][] board){
        int remDiscs = 64 - BoardHelper.getTotalStoneCount(board);
        return remDiscs % 2 == 0 ? -1 : 1;
    }


}
