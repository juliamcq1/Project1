interface Player {

    void team(char t);

}

interface Board {

    void size(int t);

}

interface State {

    void teamTurn(char team);
    boolean terminalState();

}

interface Location {

    void loctionOnBoard(int row, int col);

}

interface Move {

    boolean isPossibleForwardOne(int row, int col);
    void forwardOne(int row, int col);
    boolean isPossibleForwardTwo(int row, int col);
    void forwardTwo(int row, int col);
    boolean isPossibleCaptureLeft(int row, int col);
    void captureLeft(int row, int col);
    boolean isPossibleCaptureRight(int row, int col);
    void captureRight(int row, int col);
}

class Game implements Player, Board, Move, State{

    char team;
    int size;
    int numberOfTurns;
    int[][] board;
    char teamTurn;
    boolean isTerminalstate = false;
    int whiteUtility = 0;
    int blackUtility = 0;


    @Override
    public void team(char newTeam){

        if(newTeam == 'B' || newTeam == 'b'){
            team = 'B';
        }
        else if (newTeam == 'W' || newTeam == 'w'){
            team = 'W';
        }
        else{
            System.out.println("Did not choose a valid team (B/W). You are going to be W.");
            team = 'W';
        }

    }

    @Override
    public void size(int newSize){

        size = newSize;
        board = new int [size][size];
        
    }

    @Override
    public void teamTurn(char newTeamTurn){

        teamTurn = newTeamTurn;
        
    }

    @Override
    public boolean terminalState(){
        // Still need to check if there is a stale mate by checking if there are more possible legal moves
        for(int i=0; i<size; i++){
            if(board[1][i] == 1){                
                blackUtility=1;
                whiteUtility=-1;
                return true;
            }
            if(board[size-2][i] == 0){
                whiteUtility=1;
                blackUtility=-1;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isPossibleForwardOne(int row, int col){

        if(teamTurn == 'B'){
            if(row+1 >= size || row+1<0 || col >= size || col<0){
                return false;
            }
            else if(board[row + 1][col] != -1){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            if(row-1>= size || row-1<0 || col >= size || col<0){
                return false;
            }
            else if(board[row - 1][col] != -1){
                return false;
            }
            else{
                return true;
            }
        }
    }

    @Override
    public void forwardOne(int row, int col){


        if(teamTurn == 'B'){
            if(isPossibleForwardOne(row, col)){
                board[row][col] = -1;
                board[row + 1][col] = 0;
                teamTurn = 'W';
                terminalState();
            }
        }
        else{
            if(isPossibleForwardOne(row, col)){
                board[row][col] = -1;
                board[row - 1][col] = 1;
                teamTurn = 'B';
                terminalState();
            }
        }
    }

    @Override
    public boolean isPossibleForwardTwo(int row, int col){
        if(teamTurn == 'B'){
            if(row != 1 || col>=size || col<0){
                return false;
            }
            else if(board[row + 2][col] != -1){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            if(row != size-2 || col>=size || col<0){
                return false;
            }
            else if(board[row - 2][col] != -1){
                return false;
            }
            else{;
                return true;
            }
        }
        
    }


    @Override
    public void forwardTwo(int row, int col){
        if(teamTurn == 'B'){
            
            if(isPossibleForwardTwo(row, col)){
                board[row][col] = -1;
                board[row + 2][col] = 0;
                teamTurn = 'W';
                terminalState();
            }
        }
        else {
            if(isPossibleForwardTwo(row, col)){
                board[row][col] = -1;
                board[row - 2][col] = 1;
                teamTurn = 'B';
                terminalState();
            }
        }
        
    }

    @Override
    public boolean isPossibleCaptureLeft(int row, int col){

        if(teamTurn=='B'){
            if(row+1>=size || row+1<0 || col-1>=size || col-1<0){
                return false;
            }
            else if(board[row+1][col-1] != 1 ){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            if(row-1>=size || row-1<0 || col-1>=size || col-1<0){
                return false;
            }
            else if (board[row-1][col-1] != 0){
                return false;
            }
            else{
                return true;
            }

        }
        
    }

    @Override
    public void captureLeft(int row, int col){

        if(teamTurn=='B'){
            if(isPossibleCaptureLeft(row, col)){
                board[row][col] = -1;
                board[row+1][col-1] = 0;
                teamTurn = 'W';
                terminalState();
            }
        }
        else{
            if(isPossibleCaptureLeft(row, col)){
                board[row][col] = -1;
                board[row-1][col-1] = 1;
                teamTurn = 'B';
                terminalState();
            }
        }
        
    }

    
    @Override
    public boolean isPossibleCaptureRight(int row, int col){

        if(teamTurn=='B'){
            if(row+1>=size || row+1<0 || col+1>=size || col+1<0){
                return false;
            }
            else if(board[row+1][col+1] != 1){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            if(row-1>=size || row-1<=0 || col-1>=size || col-1<0){
                return false;
            }
            else if(board[row-1][col+1] != 0){
                return false;
            }
            else{
                return true;
            }
        }
        
    }

    @Override
    public void captureRight(int row, int col){

        if(teamTurn=='B'){
            if(isPossibleCaptureRight(row, col)){
                board[row][col] = -1;
                board[row+1][col+1] = 0;
                teamTurn = 'W';
                terminalState();
            }
        }
        else{
            if(isPossibleCaptureRight(row, col)){
                board[row][col] = -1;
                board[row-1][col+1] = 1;
                teamTurn = 'B';
                terminalState();
            }
        }
        
    }

    public void initializeBoard(){
        
        
        for (int i = 0; i < size; i++) {
            
            for (int j = 0; j < size; j++) {
                
                if(i == 1){
                    board[i][j] = 0;
                }
                else if(i == size-2){
                    board[i][j] = 1;
                }
                else{
                    board[i][j] = -1;
                }
            }
        }

        // for (int i = 0; i < size; i++){
        //     for (int j = 0; j < size; j++){
        //         System.out.print(board[i][j] + " ");
        //     }
        //     System.out.print("\n");
        // }
 
    }

    public void buildBoard(){
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p' , 'q', 'r', 's', 't', 'u', 'v', 'w',
        'x', 'w', 'z'};

        for (int i = 0; i < size; i++){
            if(i==0){
                System.out.print(" ");
                for (int j = 0; j < size; j++) {
                    if(j==0){
                        System.out.print(" ");
                    }
                    System.out.print(" " + alphabet[j]);
                }
                System.out.println("");
            }
            for (int j = 0; j < size; j++){
                if(j==0){
                    System.out.print("  ");
                }
                System.out.print("+-");
            }
            System.out.println("+");
            for (int j = 0; j < size; j++){
                if(j == 0){
                    System.out.print(i + " ");
                }
                if(board[i][j] == 0){
                    System.out.print("|♟");
                }
                else if(board[i][j] == 1){
                    System.out.print("|♙");
                }
                else{
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }

    }

    public void printStates() {
        System.out.println("team: " + team);
        System.out.println("size: " + size);
    }
}