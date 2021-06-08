import sqlite3


class DBConnector:
    def __init__(self):
        self.conn = sqlite3.connect('mo.db')

    def connect(self):
        self.conn = sqlite3.connect('mo.db')

    def disconnect(self):
        self.conn.close()

    def is_not_present(self, client_id):
        cursor = self.conn.execute("select * from clients where id = ?", (client_id,))
        return cursor.fetchone() is None

    def add_new_client(self, client_id):
        self.conn.execute("insert into clients (id, waiting, issue) values (?, ?, ?)",
                          (client_id, 0, None))
        self.conn.commit()

    def is_waiting(self, client_id):
        cursor = self.conn.execute("select waiting from clients where id = ?", (client_id,))
        waiting = 0
        for row in cursor:
            waiting = row[0]
        return waiting

    def set_waiting(self, client_id, waiting):
        self.conn.execute("update clients set waiting = ? where id = ?", (waiting, client_id))
        self.conn.commit()

    def get_prev_issue(self, client_id):
        cursor = self.conn.execute("select issue from clients where id = ?", (client_id,))
        issue = 0
        for row in cursor:
            issue = row[0]
        return issue

    def set_issue(self, client_id, issue):
        self.conn.execute("update clients set issue = ? where id = ?", (issue, client_id))
        self.conn.commit()

