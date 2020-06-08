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

data = pd.read_csv('data_to_train.csv')
x_train = data
y_train = data[['price']]
                
print('训练集x：{}\n训练集y：{}'.format(x_train.shape, y_train.shape))

def input_fn(df, label):
    feature_cols = {k: tf.constant(df[k].values) for k in FEATURES}
    label = tf.constant(label.values)
    return feature_cols, label
def train_input_fn():
    return input_fn(x_train, y_train)
def predict_input_fn():
    return {k: tf.constant(x_train[k].values) for k in FEATURES}


# 预测
# print (list(regressor.predict(input_fn=predict_input_fn, outputs=['scores', 'price'])))
# exit(0)

# 训练
regressor.fit(input_fn=train_input_fn, steps=50000)
# 测试， 这里实际上应该用测试集来测试，但是我懒得分了
score = regressor.evaluate(input_fn=train_input_fn,steps =1)
print(format(score))
sc = score['loss']
print(sc)