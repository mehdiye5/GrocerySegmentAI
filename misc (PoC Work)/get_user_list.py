
import pandas as pd

def main(product_id, predictions_fpath):
    """
    Returns a list of predicted user_id's that will by a specific product.

    Parameters
    ----------
    product_id: integer
        A unique product identifier.

    Returns
    -------
    CSV
        A CSV file consisting of one column of user_id's in the format "users_to_buy_product_<PRODUCT_ID>.csv" 
    """

    df = pd.read_csv(predictions_fpath).drop(columns='index')

    df = df[(df["product_id"] == product_id) & (df["prediction"] == 1)].reset_index(drop=True)

    df["user_id"].to_csv("users_to_buy_product_{}.csv".format(product_id), index=False)

if __name__ == "__main__":
    product_id = 196
    predictions_fpath = "./predicted_carts.csv"

    main(product_id, predictions_fpath)