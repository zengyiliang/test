
    /*
     * Copyright (c) 2017-2018, Augurframework(Chen Xiao - thunderbolt_0618@163.com), All rights reserved.
     *
     * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
     * compliance with the License. You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software distributed under the License is
     * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and limitations under the License.
     */
    package com.xhg.ops.utils;

    import com.google.common.collect.Lists;
    import com.google.common.collect.Maps;
    import java.util.List;
    import java.util.Map;
    import org.apache.commons.lang.StringUtils;
    import org.springframework.cglib.beans.BeanMap;


    /**
     * @author liuyong
     * @date 2018/7/31
     */
    public class MapUtils {

      /**
       * 将对象装换为map
       */
      public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
          BeanMap beanMap = BeanMap.create(bean);
          for (Object key : beanMap.keySet()) {
            Object val = beanMap.get(key);
            if (val != null && StringUtils.isNotBlank(val.toString())) {
              map.put(key + "", val);
            }
          }
        }
        return map;
      }

      /**
       * 将map装换为javabean对象
       */
      public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
      }

      /**
       * 将List<T>转换为List<Map<String, Object>>
       */
      public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
          Map<String, Object> map = null;
          T bean = null;
          for (int i = 0, size = objList.size(); i < size; i++) {
            bean = objList.get(i);
            map = beanToMap(bean);
            list.add(map);
          }
        }
        return list;
      }

      /**
       * 将List<Map<String,Object>>转换为List<T>
       */
      public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
          throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
          Map<String, Object> map = null;
          T bean = null;
          for (int i = 0, size = maps.size(); i < size; i++) {
            map = maps.get(i);
            bean = clazz.newInstance();
            mapToBean(map, bean);
            list.add(bean);
          }
        }
        return list;
      }

    }

