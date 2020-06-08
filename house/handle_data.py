import pandas as pd
import requests
import re
import myfunc
import json
def getLngLat(addr):
	ak = 'oC1tsRPlueIsk128sRFyKitsZB7yudtu'
	url = 'http://api.map.baidu.com/geocoding/v3/?address='+addr+'&output=json&ak='+ak+'&callback=showLocation'
	r = requests.get(url)
	res = r.text.replace('showLocation&&showLocation(', '')[0:-1]
	res = json.loads(res)
	print(res)
	return res['result']['location']

data = pd.read_csv('data.csv')
data['lng'] = 0
data['lat'] = 0
for i in range(len(data)):
    data.loc[i,'height'] = data.loc[i, 'louceng'][0]
    data.loc[i,'louceng'] = int(re.compile('共(.*)层').findall(data.loc[i, 'louceng'])[0])
    
    data.loc[i, 'shi'] = 0 if len(data.loc[i, 'huxing']) <=2 else int(data.loc[i, 'huxing'][0])
    data.loc[i, 'ting'] = 0 if len(data.loc[i, 'huxing']) <=2 else int(data.loc[i, 'huxing'][2])
    data.loc[i, 'chu'] = 0 if len(data.loc[i, 'huxing']) <=2 else int(data.loc[i, 'huxing'][4])
    data.loc[i, 'wei'] = 0 if len(data.loc[i, 'huxing']) <=2 else int(data.loc[i, 'huxing'][6])
    data.loc[i, 'mianji'] = float(data.loc[i, 'mianji'][0:-1])
    data.loc[i, 'tihubili'] = float(myfunc.getTihubili(data.loc[i, 'tihubili']))
    data.loc[i, 'price'] = int(data.loc[i, 'price'])
    try:
        addr = data.loc[i, 'qu'] + data.loc[i, 'lu'] + data.loc[i, 'xiaoqu']
        addr = getLngLat(addr)
        data.loc[i, ['lng']] = addr['lng']
        data.loc[i, ['lat']] = addr['lat']
    except :
        print("...")

print(data.loc[1,'height'])
print(data.loc[1,'louceng'])
print(data.loc[1,'shi'])
print(data.loc[1,'ting'])
print(data.loc[1,'chu'])
print(data.loc[1,'wei'])
print(data.loc[1,'mianji'])
print(data.loc[1,'tihubili'])
print(data.loc[1,'price'])
print(data.loc[1,'lng'])
print(data.loc[1,'lat'])
data.to_csv('data_to_train.csv')