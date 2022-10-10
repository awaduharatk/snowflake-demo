import os
import configparser


class Properties:

    def __init__(self, key):
        propertie_ini = configparser.ConfigParser()
        propertie_ini.read(os.path.dirname(__file__) + '/properties.ini',
                           encoding='utf-8')

        self.propertie = propertie_ini[key]
