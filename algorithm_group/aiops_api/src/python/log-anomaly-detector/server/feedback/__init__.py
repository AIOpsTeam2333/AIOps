# -*- coding: utf8 -*-

"""
Data Structure of Feedback:
  * window_id: the ID of the window
  * label: the label of type bool or binary
"""

from .feedback_entry import FeedbackEntry

from .factory import FeedbackServerFactory

from .base import AbstractFeedbackServer

from .api import ApiFeedbackServer
from .es import ElasticsearchFeedbackServer
from .mongo import MongoFeedbackServer
from .mysql import MysqlFeedbackServer
