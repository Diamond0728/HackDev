import tensorflow as tf
import pandas as pd
import random
import os
import re
import myfunc
import json
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

def categorical_column(key, values):
    return tf.feature_column.indicator_column(tf.feature_column.categorical_column_with_vocabulary_list(key, values))

FEATURES = ['dianti','jianzhuleixing','zhuangxiuqingkuang','shi',
            'ting','chu','wei','jianzhujiegou',
            'chaoxiang','height','louceng','mianji',
            'tihubili','lng', 'lat']
LABELS = ['price']

def build_model_columns():
    dianti = categorical_column('dianti',['有','无','暂无数据'])
    jianzhuleixing = categorical_column('jianzhuleixing',['板楼','塔楼'])
    zhuangxiuqingkuang = categorical_column('zhuangxiuqingkuang',['毛坯','其他','简装','精装'])
    shi = tf.feature_column.numeric_column('shi')
    ting = tf.feature_column.numeric_column('ting')
    chu = tf.feature_column.numeric_column('chu')
    wei = tf.feature_column.numeric_column('wei')
    jianzhujiegou = categorical_column('jianzhujiegou',['钢混结构','砖混结构'])
    chaoxiang = categorical_column('chaoxiang',['东','南','西','北','东南','东北','西南','西北','暂无数据'])
    height = categorical_column('height',['低','中','高'])
    louceng = tf.feature_column.numeric_column('louceng')
    mianji = tf.feature_column.numeric_column('mianji')
    tihubili = tf.feature_column.numeric_column('tihubili')
    lng = tf.feature_column.numeric_column('lng')
    lat = tf.feature_column.numeric_column('lat')


    columns = [dianti, jianzhuleixing, zhuangxiuqingkuang, shi,
               ting, chu, wei,  jianzhujiegou,
               chaoxiang, height, louceng, mianji,
               tihubili, lng, lat]
    return columns

regressor=tf.contrib.learn.DNNRegressor(
    feature_columns=build_model_columns(),
    hidden_units=[10,20,30,30,20,10],
    model_dir='DnnRegressor'
)
def predict(x_data):
    x_train = pd.DataFrame(x_data, index=[0])        
    def predict_input_fn():
        return {k: tf.constant(x_train[k].values) for k in FEATURES}
    result = list(regressor.predict(input_fn=predict_input_fn, outputs=['scores', 'price']))
    return result[0]['scores']

