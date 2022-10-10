import snowflake.connector


class dbAccessor:

    def __init__(self, account, user, password, warehouse, database, shema,
                 timeout):
        try:
            self.conn = snowflake.connector.connect(account=account,
                                                    user=user,
                                                    password=password,
                                                    warehouse=warehouse,
                                                    database=database,
                                                    shema=shema,
                                                    timeout=timeout)
        except Exception as e:
            print("init errorrrrrrrrr")
            raise (e)

    def select(self):
        print('select')
        cur = self.conn.cursor()
        cur.execute('select 1')
        rows = cur.fetchall()
        return rows

    def __del__(self):
        print('del')
        try:
            self.conn.close()
        except Exception as e:
            print('close error')
            print(e)
