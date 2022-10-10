import snowflake.connector as snow
from dbAccessor import dbAccessor
from Properties import Properties

# properties = SnowflakeProperties()
prop = Properties('snowflake')

try:
    db = dbAccessor(prop.propertie.get('account'), prop.propertie.get('user'),
                    prop.propertie.get('password'),
                    prop.propertie.get('warehouse'),
                    prop.propertie.get('database'),
                    prop.propertie.get('shema'), prop.propertie.get('timeout'))
except Exception as e:
    print('accessor error')
    print(e)

#cur = db.conn.cursor()
try:
    cur = db.conn.cursor()
    cur.execute('select 1')
    rows = cur.fetchall()
    for row in rows:
        print(row)
except Exception as e:
    print('select error')
    print(e)
