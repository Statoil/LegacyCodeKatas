namespace TennisKata
{
    public class TennisDefactored
    {
        private int p1, p2;

        public string PrintScore()
        {
            bool player1Wins = false;
            bool player2Wins = false;
            bool djuice = false;
            //Keeps track fo player 1 score
            bool p1add = false;
            bool p2add = false;

            if (p1 == 3 && p2 == 0)
            {
                player1Wins = true;
            }
            if (p1 == 0 && p2 == 3)
                player2Wins = true;
            if (p1 > 3 && p1 > p2 + 1)
                player1Wins = true;
            if (p2 > 3 && p2 > p1 + 1)
                player2Wins = true;

            // IF A PLAYER HAS NOT WON, THEN SEE IF IT IS JUICE
            if (p1 > 2 && p2 > 2 && p1 == p2)
                djuice = true;

            // IT MIGHT BE ADVANTAGE
            if (p1 > 2 && p2 > 2)
            {
                if (p1 == p2 + 1)
                    return "advantage Player1";
                if (p2 == p1 + 1)
                    return "advantage Player2";

                //ONE OF THE PLAYERS MIGHT HAVE WON
                if (p1 == p2 + 2)
                    return "Player1 wins";
                if (p2 == p1 + 2)
                    return "Player2 wins";
            }

            // PRINT THE SCORE OUT TO THE USER
            if (player1Wins)
                return "Player1 wins";
            else if (player2Wins)
                return "Player2 wins";
            else if (djuice)
                return "deuce";
            else if (p1add)
                return "advantage Player1";
            else if (p2add)
                return "advantage Player2";
            else
            {
                var returnValue = string.Format("{0} - {1}", p1, p2);
                returnValue = returnValue.Replace("0", "love");
                returnValue = returnValue.Replace("1", "fifteen");
                returnValue = returnValue.Replace("2", "thirty");
                returnValue = returnValue.Replace("3", "forty");
                return returnValue;
            }
        }

        public void Player1WinsTheBall()
        {
            p1++;
        }

        public void Player2WinsTheBall()
        {
            p2++;
        }
    }
}
